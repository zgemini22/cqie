package com.zds.file.util;

import com.zds.biz.vo.request.file.BackupTableRequest;
import com.zds.file.config.FileConfig;
import com.zds.file.dao.TblBackupFilterDao;
import com.zds.file.po.TblBackupFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseBackupUtil {

    private final FileConfig fileConfig;

    @Autowired
    private TblBackupFilterDao tblBackupFilterDao;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private String basePath;

    private static final List<String> MYSQL_SYSTEM_DBS = Arrays.asList(
            "information_schema", "mysql", "performance_schema", "sys"
    );

    private static final List<String> DM8_SYSTEM_SCHEMAS = Arrays.asList(
            "SYS", "SYSDBA", "SYSAUDITOR", "SYSBACKUP", "SYSMAN", "PUBLIC",
            "SYSFUNC", "SYSSSO", "SYSJOB", "SYSOBJ", "SYSMACPLYS", "SYSAGENT", "CTISYS"
    );


    public DatabaseBackupUtil(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    @PostConstruct
    public void init() {
        this.basePath = getSystemPath() + "/db_backups/";
    }

    private String getSystemPath() {
        String sys = System.getProperty("os.name");
        if (sys == null) {
            sys = "";
        }
        sys = sys.toLowerCase();
        return sys.contains("windows") ? fileConfig.getFileUploadPathOfWindows() : fileConfig.getFileUploadPathOfLinux();
    }

    public void backupAllDatabases() throws Exception {
        List<TblBackupFilter> tblBackupFilters = tblBackupFilterDao.selectList(TblBackupFilter.getWrapper().eq(TblBackupFilter::getState, false)
                .eq(TblBackupFilter::getDeleted, false));

        String dbType = getDatabaseType();
        List<String> databases = getDatabaseList(dbType);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path backupDir = Paths.get(basePath, today);
        Files.createDirectories(backupDir);

        long totalStartTime = System.currentTimeMillis();
        List<String> backupStats = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(datasourceUrl, username, password)) {
            for (String dbName : databases) {
                String backupFile = backupDir.resolve(dbName + ".sql").toString();
                long dbStartTime = System.currentTimeMillis();

                backupDatabaseJdbc(conn, dbType, dbName, backupFile , tblBackupFilters);

                long dbCostTime = System.currentTimeMillis() - dbStartTime;
                long fileSize = Files.size(Paths.get(backupFile));
                String sizeStr = String.format("%.2f MB", fileSize / (1024.0 * 1024));
                backupStats.add(String.format("库名: %s, 耗时: %d ms, 文件大小: %s, 路径: %s",
                        dbName, dbCostTime, sizeStr, backupFile));
            }
        }

        long totalCostTime = System.currentTimeMillis() - totalStartTime;
        backupStats.add(String.format("\n总备份库数量: %d, 总耗时: %d ms", databases.size(), totalCostTime));

        System.out.println("===== 备份统计信息 =====");
        backupStats.forEach(System.out::println);
        System.out.println("=======================");

        cleanupOldBackups();
    }

    public void backupSpecificTables(List<BackupTableRequest> list) throws Exception {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("指定备份列表不能为空");
        }

        String dbType = getDatabaseType();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path backupDir = Paths.get(basePath, today);
        Files.createDirectories(backupDir);

        try (Connection conn = DriverManager.getConnection(datasourceUrl, username, password)) {

            for (BackupTableRequest item : list) {

                String dbName = item.getDatabaseName();
                String table = item.getTableName();

                if (dbName == null || table == null) {
                    System.out.println("跳过空对象: " + item);
                    continue;
                }

                // 输出文件名：库名_表名.sql
                String backupFile = backupDir.resolve(dbName + "_" + table + ".sql").toString();

                System.out.println("开始备份: " + dbName + "." + table);

                try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(backupFile))) {

                    // 写表结构
                    String ddl = getCreateTable(conn, dbType, dbName, table);
                    writer.write(ddl);
                    writer.newLine();
                    writer.write("-- INSERT DATA\n");

                    // 写数据
                    if ("mysql".equals(dbType)) {
                        try (PreparedStatement ps = conn.prepareStatement(
                                "SELECT * FROM `" + dbName + "`.`" + table + "`",
                                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

                            ps.setFetchSize(Integer.MIN_VALUE);

                            try (ResultSet rs = ps.executeQuery()) {
                                writeMysqlInserts(writer, dbName, table, rs);
                            }
                        }
                    } else {
                        String owner = dbName.toUpperCase();
                        try (PreparedStatement ps = conn.prepareStatement(
                                "SELECT * FROM \"" + owner + "\".\"" + table.toUpperCase() + "\"",
                                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                             ResultSet rs = ps.executeQuery()) {

                            writeDmInserts(writer, owner, table, rs);
                        }
                    }
                }

                System.out.println("备份完成: " + dbName + "." + table + " -> " + backupFile);
            }
        }

        System.out.println("指定库表备份完成，目录：" + backupDir);
    }


    private String getDatabaseType() {
        if (datasourceUrl.startsWith("jdbc:mysql")) {
            return "mysql";
        } else if (datasourceUrl.startsWith("jdbc:dm")) {
            return "dm8";
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + datasourceUrl);
        }
    }

    private List<String> getDatabaseList(String dbType) throws SQLException {
        List<String> dbs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(datasourceUrl, username, password)) {
            if ("mysql".equals(dbType)) {
                try (PreparedStatement ps = conn.prepareStatement("SHOW DATABASES");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(1);
                        if (!MYSQL_SYSTEM_DBS.contains(name)) {
                            dbs.add(name);
                        }
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement("SELECT NAME FROM SYS.SYSOBJECTS WHERE TYPE$='SCH'");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String schema = rs.getString("NAME").toUpperCase();
                        if (!DM8_SYSTEM_SCHEMAS.contains(schema)) {
                            dbs.add(schema);
                        }
                    }
                }
            }
        }
        System.out.println("需要备份的库/模式：" + dbs);
        return dbs;
    }

    private void backupDatabaseJdbc(Connection conn, String dbType, String dbName, String backupFile,
                                    List<TblBackupFilter> tblBackupFilters)
            throws SQLException, IOException {

        List<String> tables = new ArrayList<>();

        if ("mysql".equals(dbType)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA=?")) {
                ps.setString(1, dbName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        tables.add(rs.getString("TABLE_NAME"));
                    }
                }
            }
        } else {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT TABLE_NAME FROM DBA_TABLES WHERE OWNER=?")) {
                ps.setString(1, dbName.toUpperCase());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        tables.add(rs.getString("TABLE_NAME"));
                    }
                }
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(backupFile))) {

            for (String table : tables) {
                //过滤
                boolean skip = tblBackupFilters.stream().anyMatch(f ->
                        f.getDatabaseName().equalsIgnoreCase(dbName)
                                && f.getTableName().equalsIgnoreCase(table)
                );
                if (skip) {
                    System.out.println("跳过备份表: " + dbName + "." + table);
                    continue;
                }

                // 写表结构
                String ddl = getCreateTable(conn, dbType, dbName, table);
                writer.write(ddl);
                writer.newLine();

                // 写数据
                writer.write("-- INSERT DATA\n");
                if ("mysql".equals(dbType)) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM `" + dbName + "`.`" + table + "`",
                            ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                        ps.setFetchSize(Integer.MIN_VALUE);
                        try (ResultSet rs = ps.executeQuery()) {
                            writeMysqlInserts(writer, dbName, table, rs);
                        }
                    }
                } else {
                    String owner = dbName.toUpperCase();
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM \"" + owner + "\".\"" + table.toUpperCase() + "\"",
                            ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                         ResultSet rs = ps.executeQuery()) {
                        writeDmInserts(writer, owner, table, rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("备份完成: " + dbName + " -> " + backupFile);
    }

    /** MySQL 边读边写 INSERT */
    private void writeMysqlInserts(BufferedWriter writer, String schema, String table, ResultSet rs) throws SQLException, IOException {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        List<String> colNames = new ArrayList<>();
        int[] colTypes = new int[colCount];
        for (int i = 1; i <= colCount; i++) {
            colNames.add("`" + meta.getColumnName(i) + "`");
            colTypes[i - 1] = meta.getColumnType(i);
        }

        while (rs.next()) {
            StringBuilder row = new StringBuilder("(");
            for (int i = 1; i <= colCount; i++) {
                Object val = rs.getObject(i);
                int type = colTypes[i - 1];

                if (val == null) {
                    row.append("NULL");
                } else {
                    switch (type) {
                        case Types.BIT:
                        case Types.BOOLEAN:
                            boolean b = (val instanceof Boolean) ? (Boolean) val :
                                    (val instanceof Number ? ((Number) val).intValue() != 0 : "true".equalsIgnoreCase(val.toString()));
                            row.append(b ? 1 : 0);
                            break;
                        case Types.TINYINT:
                        case Types.SMALLINT:
                        case Types.INTEGER:
                        case Types.BIGINT:
                        case Types.NUMERIC:
                        case Types.DECIMAL:
                        case Types.FLOAT:
                        case Types.DOUBLE:
                            if (val instanceof BigDecimal) {
                                row.append(((BigDecimal) val).toPlainString());
                            } else {
                                row.append(val.toString());
                            }
                            break;
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP:
                            row.append("'").append(val.toString()).append("'");
                            break;
                        default:
                            row.append("'").append(val.toString().replace("'", "''")).append("'");
                    }
                }
                if (i < colCount) {
                    row.append(", ");
                }
            }
            row.append(")");
            writer.write("INSERT INTO `" + schema + "`.`" + table + "` (" + String.join(", ", colNames) + ") VALUES " + row + ";\n");
        }
    }

    /** DM8 边读边写 INSERT */
    private void writeDmInserts(BufferedWriter writer, String schema, String table, ResultSet rs) throws SQLException, IOException {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        List<String> colNames = new ArrayList<>();
        for (int i = 1; i <= colCount; i++) {
            colNames.add("\"" + meta.getColumnName(i).toLowerCase() + "\"");
        }

        while (rs.next()) {
            StringBuilder row = new StringBuilder("(");
            for (int i = 1; i <= colCount; i++) {

                Object val = rs.getObject(i);

                if (val == null) {
                    row.append("NULL");

                } else if (val instanceof java.sql.Clob) {

                    // === 读取 CLOB 内容 ===
                    StringBuilder sb = new StringBuilder();
                    try (Reader reader = ((Clob) val).getCharacterStream()) {
                        char[] buffer = new char[4096];
                        int len;
                        while ((len = reader.read(buffer)) != -1) {
                            sb.append(buffer, 0, len);
                        }
                    }
                    String clobText = sb.toString().replace("'", "''");
                    row.append("'").append(clobText).append("'");

                } else if (val instanceof BigDecimal) {

                    row.append(((BigDecimal) val).toPlainString());

                } else {

                    row.append("'").append(val.toString().replace("'", "''")).append("'");
                }

                if (i < colCount) {
                    row.append(", ");
                }
            }
            row.append(")");
            writer.write("INSERT INTO \"" + schema + "\".\"" + table + "\" (" + String.join(", ", colNames) + ") VALUES " + row + ";\n");
        }
    }


    private void cleanupOldBackups() throws IOException {
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            return;
        }

        File[] dirs = baseDir.listFiles(File::isDirectory);
        if (dirs == null || dirs.length <= 2) {
            return;
        }

        List<File> sorted = Arrays.stream(dirs)
                .sorted(Comparator.comparing(File::getName).reversed())
                .collect(Collectors.toList());

        for (int i = 2; i < sorted.size(); i++) {
            deleteDirectory(sorted.get(i).toPath());
            System.out.println("已删除旧备份目录: " + sorted.get(i).getName());
        }
    }

    private void deleteDirectory(Path path) throws IOException {
        if (Files.notExists(path)) {
            return;
        }
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (IOException ignored) {}
                });
    }

    public String getCreateTable(Connection conn, String dbType, String dbName, String tableName) throws Exception {
        StringBuilder ddl = new StringBuilder();
        String fullTableName;

        if ("mysql".equalsIgnoreCase(dbType)) {
            fullTableName = "`" + dbName + "`.`" + tableName + "`";
        } else { // DM8
            fullTableName = "\"" + dbName + "\".\"" + tableName + "\"";
        }
        ddl.append("CREATE TABLE ").append(fullTableName).append(" (\n");

        String columnSql;
        String pkSql;

        if ("mysql".equalsIgnoreCase(dbType)) {
            columnSql = "SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT, COLUMN_COMMENT, EXTRA " +
                    "FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION";

            pkSql = "SELECT COLUMN_NAME FROM information_schema.KEY_COLUMN_USAGE " +
                    "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND CONSTRAINT_NAME = 'PRIMARY'";
        } else { // DM8
            columnSql = "SELECT c.COLUMN_NAME, c.DATA_TYPE, c.DATA_LENGTH, c.DATA_PRECISION, c.DATA_SCALE, " +
                    "c.NULLABLE, c.DATA_DEFAULT, cc.COMMENTS " +
                    "FROM ALL_TAB_COLUMNS c " +
                    "LEFT JOIN ALL_COL_COMMENTS cc ON c.TABLE_NAME = cc.TABLE_NAME AND c.COLUMN_NAME = cc.COLUMN_NAME " +
                    "WHERE c.OWNER = ? AND c.TABLE_NAME = ? ORDER BY c.COLUMN_ID";

            pkSql = "SELECT COLUMN_NAME FROM ALL_CONS_COLUMNS WHERE TABLE_NAME = ? AND CONSTRAINT_NAME = " +
                    "(SELECT CONSTRAINT_NAME FROM ALL_CONSTRAINTS WHERE TABLE_NAME = ? AND OWNER = ? AND CONSTRAINT_TYPE = 'P')";
        }

        // 查询列信息
        PreparedStatement ps = conn.prepareStatement(columnSql);
        ps.setString(1, dbName);
        ps.setString(2, tableName);
        ResultSet rs = ps.executeQuery();

        // 查询主键
        List<String> pkList = new ArrayList<>();
        PreparedStatement pkPs = conn.prepareStatement(pkSql);
        if ("mysql".equalsIgnoreCase(dbType)) {
            pkPs.setString(1, dbName);
            pkPs.setString(2, tableName);
        } else {
            pkPs.setString(1, tableName);
            pkPs.setString(2, tableName);
            pkPs.setString(3, dbName);
        }
        ResultSet pkRs = pkPs.executeQuery();
        while (pkRs.next()) {
            pkList.add(pkRs.getString("COLUMN_NAME"));
        }

        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");

            String dataType = "mysql".equalsIgnoreCase(dbType)
                    ? rs.getString("COLUMN_TYPE")
                    : buildDmColumnType(rs);

            String nullable = getNullableValue(dbType, rs);
            String defaultValue = getDefaultValue(dbType, rs);
            String comment = getComment(dbType, rs);

            String extra = "";
            if ("mysql".equalsIgnoreCase(dbType) && pkList.contains(columnName)) {
                // MySQL 主键自增
                String lowerType = dataType.toLowerCase();
                if (lowerType.startsWith("bigint") || lowerType.startsWith("int")) {
                    extra = " AUTO_INCREMENT";
                }
            } else if ("dm8".equalsIgnoreCase(dbType) && pkList.contains(columnName)) {
                // DM8 主键自增
                String lowerType = dataType.toLowerCase();
                if (lowerType.startsWith("bigint") || lowerType.startsWith("number") || lowerType.startsWith("int")) {
                    extra = " IDENTITY(1,1)";
                }
            }

            if ("dm8".equalsIgnoreCase(dbType)) {
                ddl.append("  \"").append(columnName).append("\" ").append(dataType)
                        .append(extra).append(nullable).append(defaultValue).append(comment).append(",\n");
            } else {
                ddl.append("  ").append(columnName).append(" ").append(dataType)
                        .append(nullable).append(defaultValue).append(extra).append(comment).append(",\n");
            }
        }

        if (!pkList.isEmpty()) {
            if ("mysql".equalsIgnoreCase(dbType)) {
                ddl.append("  PRIMARY KEY (").append(String.join(",", pkList)).append(")\n");
            } else { // DM8
                ddl.append("  NOT CLUSTER PRIMARY KEY(")
                        .append(pkList.stream().map(c -> "\"" + c + "\"").collect(Collectors.joining(",")))
                        .append(")\n");
            }
        } else {
            ddl.setLength(ddl.length() - 2);
            ddl.append("\n");
        }

        if ("dm8".equalsIgnoreCase(dbType)) {
            ddl.append(") STORAGE(ON \"MAIN\", CLUSTERBTR);");
        } else {
            ddl.append(");");
        }

        return ddl.toString();
    }


    /** DM8 字段类型组装（解决 BigDecimal 类型精度） */
    private String buildDmColumnType(ResultSet rs) throws Exception {
        String dataType = rs.getString("DATA_TYPE");

        Integer length = getIntValue(rs.getObject("DATA_LENGTH"));
        Integer precision = getIntValue(rs.getObject("DATA_PRECISION"));
        Integer scale = getIntValue(rs.getObject("DATA_SCALE"));

        if ("VARCHAR".equalsIgnoreCase(dataType) || "CHAR".equalsIgnoreCase(dataType)) {
            return dataType + "(" + length + ")";
        } else if ("NUMBER".equalsIgnoreCase(dataType) && precision != null) {
            if (scale != null && scale > 0) {
                return "DECIMAL(" + precision + "," + scale + ")";
            } else {
                return "DECIMAL(" + precision + ")";
            }
        }
        return dataType;
    }

    /** 统一空字段处理 */
    private String getNullableValue(String dbType, ResultSet rs) throws Exception {
        String nullable = "mysql".equalsIgnoreCase(dbType)
                ? rs.getString("IS_NULLABLE")
                : rs.getString("NULLABLE");

        if ("NO".equalsIgnoreCase(nullable) || "N".equalsIgnoreCase(nullable)) {
            return " NOT NULL";
        }
        return "";
    }

    /** 默认值 */
    private String getDefaultValue(String dbType, ResultSet rs) throws Exception {
        Object val = "mysql".equalsIgnoreCase(dbType)
                ? rs.getObject("COLUMN_DEFAULT")
                : rs.getObject("DATA_DEFAULT");

        if (val == null) return "";
        String def = val.toString().trim();
        if (def.isEmpty()) return "";
        return " DEFAULT " + def;
    }

    /** 注释 */
    private String getComment(String dbType, ResultSet rs) throws Exception {
        String c = "mysql".equalsIgnoreCase(dbType)
                ? rs.getString("COLUMN_COMMENT")
                : rs.getString("COMMENTS");

        if (c == null || c.isEmpty()) return "";
        return " COMMENT '" + c + "'";
    }

    /** BigDecimal → Integer 安全转换 */
    private Integer getIntValue(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof BigDecimal) return ((BigDecimal) obj).intValue();
        return Integer.parseInt(obj.toString());
    }

}
