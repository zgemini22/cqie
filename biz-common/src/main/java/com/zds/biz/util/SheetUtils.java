package com.zds.biz.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetUtils {

    public static int getLastRowNum(Sheet sheet, int cellNum) {
        int totalRowNum = sheet.getLastRowNum();
        int num = totalRowNum;
        for (int i = totalRowNum; i > 0; i--) {
            Row row = sheet.getRow(i);
            int check = 0;
            if (row != null) {
                for (int k = 0; k < cellNum; k++) {
                    Cell cell = row.getCell(k);
                    String value;
                    if (cell == null) {
                        value = "";
                    } else {
                        cell.setCellType(CellType.STRING);
                        value = cell.getStringCellValue().trim();
                    }
                    if ("".equals(value)) {
                        check ++;
                    }
                }
                if (check == cellNum) {
                    num --;
                } else {
                    break;
                }
            } else {
                num --;
            }

        }
        return num;
    }
}

