package com.zds.biz.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Description: MYSQL表数据转EXCEL
 * Author: wangzhipeng
 * Date: 2024/8/21
 */
public class MySQLToExcelUtil {

    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1qaz!QAZ";

    public static void exportTableStructuresToExcel(Map<String,Set<String>> dataMap, String outputFilePath) throws SQLException, IOException {
        Workbook workbook = new XSSFWorkbook();
        for (String dataName : dataMap.keySet()) {
            String JDBC_URL = "jdbc:mysql://192.168.39.114:3306/"+dataName+"?autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true";
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                for (String tableName : dataMap.get(dataName)) {
                    String query =  "SELECT(SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '"+dataName+"' AND TABLE_NAME = '"+tableName+"') AS table_comment,COLUMN_NAME AS column_name,COLUMN_COMMENT AS column_comment FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+dataName+"' AND TABLE_NAME = '"+tableName+"' ORDER BY ORDINAL_POSITION;";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    Sheet sheet =  null;
                    // 创建一个工作表
                    if (resultSet.next()) {
                        sheet = workbook.createSheet(resultSet.getString("table_comment"));
                    } else {
                        // 处理结果集为空的情况
                        sheet = workbook.createSheet(tableName);
                    }
                    resultSet.previous();
                    // 在工作表中创建一行
                    Row headerRow = sheet.createRow(0);

                    int rowNum = 0;
                    while (resultSet.next()) {
                        String columnName = resultSet.getString("column_name");
                        String columnComment = resultSet.getString("column_comment");
                        System.out.println("列名:"+columnComment);
                        // 在第一行中创建一个单元格，并设置单元格的值
                        Cell headerCell = headerRow.createCell(rowNum++);
                        headerCell.setCellValue(columnComment.isEmpty() ? columnName : columnComment);
                    }
                    resultSet.close();
                    statement.close();
                }
            }
        }
        // 关闭工作簿（实际使用时应保存到文件）
        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    public static void main(String[] args) {
        Map<String,Set<String>> dataMap = new HashMap<>();
        Set<String> gas_data_device_tableNames = new HashSet<>(Arrays.asList("gas_device_type","gas_device_info",
                "gas_device_info_file","gas_device_data_collection"));
        Set<String> gas_data_info_tableNames = new HashSet<>(Arrays.asList("gas_construct_patrol_detail","gas_construct_prevent",
                "gas_pipeline_patrol_plan","gas_security_patrol_plan",
                "gas_security_patrol_plan_custom","gas_security_patrol_record",
                "gas_station_patrol_detail","gas_station_patrol_plan"));
        dataMap.put("gas_data_device",gas_data_device_tableNames);
        dataMap.put("gas_data_info",gas_data_info_tableNames);
        String outputFilePath = "C:\\Users\\9-0309\\Desktop\\导出表结构\\ceshi.xlsx";
        try {
            exportTableStructuresToExcel(dataMap, outputFilePath);
            System.out.println("导出成功");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
