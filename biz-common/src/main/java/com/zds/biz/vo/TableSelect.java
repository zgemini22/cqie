package com.zds.biz.vo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

/**
 * excel级联下拉框
 */
public class TableSelect {
    /**
     * poi对象
     */
    private XSSFWorkbook workbook;

    /**
     * 数据
     */
    private Map<String, List<String>> areaList;

    /**
     * 隐藏页名称
     */
    private String hiddenSheetName = "hidden";

    /**
     * 第一行
     */
    private int firstRow = 1;

    /**
     * 一级名称
     */
    private String topName;

    /**
     * 级联集合
     */
    private List<Integer> selectColList;

    public TableSelect(String topName, Map<String, List<String>> areaList, int firstRow, List<Integer> selectColList) {
        this.topName = topName;
        this.firstRow = firstRow;
        this.areaList = areaList;
        this.selectColList = selectColList;
    }

    public XSSFWorkbook setCascadeDropDownBox(XSSFWorkbook workbook) {
        this.workbook = workbook;
        return setCascadeDropDownBox();
    }

    /**
     * 设置二级级联下拉框数据
     */
    public XSSFWorkbook setCascadeDropDownBox() {
        XSSFSheet mainSheet;
        Sheet sheet = workbook.getSheet("Sheet0");
        if (Objects.nonNull(sheet)) {
            mainSheet = (XSSFSheet) sheet;
        } else {
            mainSheet = (XSSFSheet) workbook.createSheet("Sheet0");
        }
        //获取所有sheet页个数
        int sheetTotal = workbook.getNumberOfSheets();
        //处理下拉数据
        if (areaList != null && areaList.size() != 0) {
            //新建一个sheet页
            XSSFSheet hiddenSheet = workbook.getSheet(hiddenSheetName);
            if (hiddenSheet == null) {
                hiddenSheet = workbook.createSheet(hiddenSheetName);
                sheetTotal++;
            }
            int mainStart = 2;
            int mainEnd = mainStart;
            // 获取数据起始行
            int startRowNum = hiddenSheet.getLastRowNum() + 1;
            Set<String> keySet = areaList.keySet();
            //key重排序
            Map<String, String> item = new LinkedHashMap<>();
            item.put(topName, topName);
            for (String code : areaList.get(topName)) {
                item.put(code, code);
            }
            for (String key : keySet) {
                if (!item.containsKey(key)) {
                    item.put(key, key);
                }
            }
            for (String key : item.keySet()) {
                XSSFRow fRow = hiddenSheet.createRow(startRowNum++);
                fRow.createCell(0).setCellValue(key);
                List<String> sons = areaList.get(key);
                for (int i = 1; i <= sons.size(); i++) {
                    fRow.createCell(i).setCellValue(sons.get(i - 1));
                }
                if (Objects.equals(topName, key)) {
                    mainEnd = sons.size();
                }
                // 添加名称管理器
                String range = getRange(1, startRowNum, sons.size());
                Name name = workbook.getName(key);
                if (Objects.isNull(name)) {
                    name = workbook.createName();
                    //key不可重复
                    name.setNameName(key);
                    String formula = hiddenSheetName + "!" + range;
                    name.setRefersToFormula(formula);
                }
            }
            //将数据字典sheet页隐藏掉
            workbook.setSheetHidden(sheetTotal - 1, true);

            // 设置父级下拉
            //获取新sheet页内容
            String mainFormula = hiddenSheetName + "!$A$" + (mainStart + 1) + ":$A$" + (mainEnd + 2);

            for (int i = 0; i < selectColList.size(); i++) {
                Integer col = selectColList.get(i);
                if (i == 0) {
                    // 设置下拉列表值绑定到主sheet页具体哪个单元格起作用
                    mainSheet.addValidationData(setDataValidation(mainFormula, firstRow, col, col));
                } else {
                    Integer fatherCol = selectColList.get(i - 1);
                    // 设置子级下拉
                    // 当前列为子级下拉框的内容受父级哪一列的影响
                    String indirectFormula = "INDIRECT($" + decimalToTwentyHex(fatherCol + 1) + "" + (firstRow + 1) + ")";
                    mainSheet.addValidationData(setDataValidation(indirectFormula, firstRow, col, col));
                }
            }
        }
        return this.workbook;
    }

    /**
     * 计算formula
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     */
    private String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            // 26-51之间，包括边界（仅两次字母表计算）
            if ((colCount - 25) / 26 == 0 || colCount == 51) {
                // 边界值
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }

    /**
     * 返回类型 DataValidation
     * @param strFormula formula
     * @param firstRow   起始行
     * @param firstCol   起始列
     * @param endCol     终止列
     * @return 返回类型 DataValidation
     */
    private DataValidation setDataValidation(String strFormula, int firstRow, int firstCol, int endCol) {
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, 65535, firstCol, endCol);
        DataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) workbook.getSheet(hiddenSheetName));
        DataValidationConstraint formulaListConstraint = dvHelper.createFormulaListConstraint(strFormula);
        return dvHelper.createValidation(formulaListConstraint, regions);
    }

    /**
     * 十进制转二十六进制
     */
    private String decimalToTwentyHex(int decimalNum) {
        StringBuilder result = new StringBuilder();
        while (decimalNum > 0) {
            int remainder = decimalNum % 26;
            //大写A的ASCII码值为65
            result.append((char) (remainder + 64));
            decimalNum = decimalNum / 26;
        }
        return result.reverse().toString();
    }
}
