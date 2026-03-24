package test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ExcelTest {

    public static void main(String[] args) {
        ExcelTest.test();
    }

    public static void test() {
        String filePath = "C:\\Users\\KMC\\Desktop\\test1.xlsx";
        int sheetNum = 0;//下标从0开始
        int rowStart = 0;//下标从0开始
        int rowEnd = 2;//下标从0开始
        Map<String, Integer> map = new HashMap<>();//key为输出后的字段名称,value为列下标
        map.put("company", 0);
        map.put("aa", 1);
        map.put("bb", 2);
        map.put("cc", 3);
        map.put("dd", 4);
        map.put("aa1", 5);
        map.put("bb1", 6);
        map.put("cc1", 7);
        map.put("dd1", 8);
        map.put("aa2", 9);
        map.put("bb2", 10);
        map.put("cc2", 11);
        map.put("dd2", 12);
        map.put("aa3", 13);
        map.put("bb3", 14);
        map.put("cc3", 15);
        map.put("dd3", 16);
//        map.put("aa4", 17);
//        map.put("bb4", 18);
//        map.put("cc4", 19);
//        map.put("dd4", 20);
        Set<Integer> percentRowIndex = new HashSet<>();//百分比格式的列下标
        percentRowIndex.add(2);
        percentRowIndex.add(3);
        percentRowIndex.add(4);
        percentRowIndex.add(6);
        percentRowIndex.add(7);
        percentRowIndex.add(8);
        percentRowIndex.add(10);
        percentRowIndex.add(11);
        percentRowIndex.add(12);
        percentRowIndex.add(14);
        percentRowIndex.add(15);
        percentRowIndex.add(16);
        percentRowIndex.add(18);
        percentRowIndex.add(19);
        percentRowIndex.add(20);
        boolean perFlag = true;//百分比格式的列,转换后是否拼接%
        String json = excelToJson(filePath, sheetNum, rowStart, rowEnd, map, percentRowIndex, perFlag);
        log.info("\n" + json.replaceAll(" ", ""));
    }

    public static String excelToJson(String filePath, int sheetNum, int rowStart, int rowEnd, Map<String, Integer> map) {
        return excelToJson(filePath, sheetNum, rowStart, rowEnd, map, new HashSet<>(), false);
    }

    public static String excelToJson(String filePath, int sheetNum, int rowStart, int rowEnd, Map<String, Integer> map, Set<Integer> percentRowIndex, boolean perFlag) {
        try {
            Map<Integer, Integer> percentMap = new HashMap<>();
            if (percentRowIndex != null && percentRowIndex.size() > 0) {
                percentMap = percentRowIndex.stream()
                        .collect(Collectors.toMap(x -> x, x -> x, (a, b) -> b));
            }
            File excelFile = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(sheetNum);
            List<Map<String, String>> list = new ArrayList<>();
            for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Map<String, String> rowMap = new HashMap<>();
                for (String key : map.keySet()) {
                    Integer index = map.get(key);
                    rowMap.put(key, convertCellValueToString(row.getCell(index), percentMap.containsKey(index), perFlag));
                }
                list.add(rowMap);
            }
            return JSON.toJSONString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertCellValueToString(Cell cell, boolean percent, boolean perFlag) {
        if (null == cell) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellTypeEnum()) {
            case STRING:  //字符串
                returnValue = cell.getStringCellValue();
                break;
            case NUMERIC: //数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    returnValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    double numericCellValue = cell.getNumericCellValue();
                    boolean isInteger = isIntegerForDouble(numericCellValue);
                    if (isInteger) {
                        DecimalFormat df = new DecimalFormat("0");
                        returnValue = df.format(numericCellValue);
                    } else {
                        returnValue = Double.toString(numericCellValue);
                    }
                    if (percent) {
                        returnValue = new BigDecimal(returnValue)
                                .multiply(new BigDecimal("100"))
                                .setScale(2, BigDecimal.ROUND_HALF_UP)
                                .toPlainString();
                        if (perFlag) {
                            returnValue = returnValue + "%";
                        }
                    }
                }
                break;
            case BOOLEAN: //布尔
                boolean booleanCellValue = cell.getBooleanCellValue();
                returnValue = Boolean.toString(booleanCellValue);
                break;
            case BLANK: //空值
                break;
            case FORMULA: //公式
                cell.getCellFormula();
                break;
            case ERROR: //故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    public static boolean isIntegerForDouble(Double num) {
        double eqs = 1e-10; //精度范围
        return num - Math.floor(num) < eqs;
    }
}
