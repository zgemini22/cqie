import com.zds.biz.vo.request.file.BackupTableRequest;
import com.zds.file.FileApplication;
import com.zds.file.util.DatabaseBackupUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(classes = FileApplication.class)
public class test {
    @Autowired
    private DatabaseBackupUtil databaseBackupUtil;

    @Test
    public void test1() throws Exception {
//        databaseBackupUtil.backupAllDatabases();
        List<BackupTableRequest> list = new ArrayList<>();
        BackupTableRequest request = new BackupTableRequest();
        request.setDatabaseName("GAS_DATA_INFO");
        request.setTableName("gas_station_info");
        list.add(request);
        databaseBackupUtil.backupSpecificTables(list);
    }


    @Test
    public void test3() throws Exception {
        String jdbcUrl = "jdbc:mysql://36.140.200.230:3306/gas_data_user?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true";
        String username = "root";
        String password = "RQ#3eTaJWKAO6dD&ykwo";
        String sqlFilePath = "C:\\Users\\llp\\Desktop\\gas_data_user-tbl_area_node2.sql";
        executeSqlFile(jdbcUrl, username, password, sqlFilePath);
    }

    /**
     * 执行 SQL 文件
     */
    public static void executeSqlFile(String jdbcUrl, String username, String password, String sqlFilePath) {
        // try-with-resources 保证连接和文件自动关闭
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {

            conn.setAutoCommit(false); // 提高性能 & 控制事务提交

            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            int executedCount = 0;

            try (Statement stmt = conn.createStatement()) {
                while ((line = reader.readLine()) != null) {
                    // 去除注释与空行
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--") || line.startsWith("#") || line.startsWith("//")) {
                        continue;
                    }

                    sqlBuilder.append(line).append(" ");

                    // 检测语句结束符
                    if (line.endsWith(";")) {
                        String sql = sqlBuilder.toString().trim();
                        sql = sql.substring(0, sql.length() - 1); // 去掉最后的分号
                        sqlBuilder.setLength(0);

                        try {
                            stmt.execute(sql);
                            executedCount++;
                            if (executedCount % 100 == 0) {
                                conn.commit(); // 每 100 条提交一次，防止内存堆积
                                System.out.println("已执行 " + executedCount + " 条语句...");
                            }
                        } catch (SQLException e) {
                            System.err.println("执行出错的 SQL: " + sql);
                            e.printStackTrace();
                        }
                    }
                }

                // 提交剩余
                conn.commit();
                System.out.println("✅ SQL 文件执行完成，共执行 " + executedCount + " 条语句。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
