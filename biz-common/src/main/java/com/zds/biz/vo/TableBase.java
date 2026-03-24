package com.zds.biz.vo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表格-基础包装
 */
public class TableBase {
    /**
     * 文件名
     */
    private String excelName;

    /**
     * 表头集合
     */
    private List<List<TableTdBase>> theadList;

    /**
     * 设置背景颜色
     */
    private Boolean isStyle = false;

    /**
     * 行数据集合
     */
    private List<List<TableTdBase>> tbodyList;

    /**
     * 级联下拉框数据集合
     */
    private List<TableSelect> tableSelectList;

    public List<List<TableTdBase>> getTheadList() {
        return theadList;
    }

    public void setTheadList(List<List<TableTdBase>> theadList) {
        this.theadList = theadList;
    }

    public List<List<TableTdBase>> getTbodyList() {
        return tbodyList;
    }

    public void setTbodyList(List<List<TableTdBase>> tbodyList) {
        this.tbodyList = tbodyList;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public Boolean getIsStyle() {
        return isStyle;
    }

    public void setIsStyle(Boolean isStyle) {
        this.isStyle = isStyle;
    }

    public List<TableSelect> getTableSelectList() {
        return tableSelectList;
    }

    public void setTableSelectList(List<TableSelect> tableSelectList) {
        this.tableSelectList = tableSelectList;
    }

    /**
     * 设置单行表头内容
     * 无跨行跨列
     */
    public void setThList(List<String> thList) {
        theadList = new ArrayList<>();
        List<TableTdBase> row = new ArrayList<>();
        for (String th : thList) {
            TableTdBase tdBase = new TableTdBase();
            tdBase.setValue(th);
            row.add(tdBase);
        }
        theadList.add(row);
    }

    /**
     * 设置多行表体内容
     * 无跨行跨列
     */
    public void setTrList(List<List<String>> trList) {
        tbodyList = new ArrayList<>();
        for (List<String> tdList : trList) {
            List<TableTdBase> row = new ArrayList<>();
            for (String td : tdList) {
                TableTdBase tdBase = new TableTdBase();
                tdBase.setValue(td);
                row.add(tdBase);
            }
            tbodyList.add(row);
        }
    }

    /**
     * 导出
     */
    public void export(String exportFileName, HttpServletResponse response) {
        replaceHead();
        List<List<TableTdBase>> lineList = theadList;
        if (tbodyList != null && tbodyList.size() > 0) {
            lineList.addAll(tbodyList);
        }
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直对齐
        XSSFDataFormat dataFormat = wb.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        //单页
        Sheet sheet = wb.createSheet();
        int rowNum = 0;
        for (List<TableTdBase> trlist : lineList) {
            //行
            Row row = sheet.createRow(rowNum);
            row.setHeight((short) 400);
            int cellNum = 0;
            for (TableTdBase tdBase : trlist) {
                //偏移计算
                List<CellRangeAddress> addressList = sheet.getMergedRegions();
                cellNum = checkCellNum(addressList, cellNum, rowNum);
                //单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(tdBase.getValue() == null ? "" : tdBase.getValue());
                if (tdBase.getRowspan() > 1 || tdBase.getColspan() > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + tdBase.getRowspan() - 1,
                            cellNum, cellNum + tdBase.getColspan() - 1));
                }
                cellNum += tdBase.getColspan();
            }
            rowNum++;
        }
        int num = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i <= num; i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            if (colWidth < 255 * 256) {
                sheet.setColumnWidth(i, Math.max(colWidth, 3000));
            } else {
                sheet.setColumnWidth(i, 6000);
            }
            //统一列的单元格格式
            sheet.setDefaultColumnStyle(i, cellStyle);
        }
        //添加级联下拉框
        if (tableSelectList != null) {
            for (TableSelect tableSelect : tableSelectList) {
                wb = tableSelect.setCascadeDropDownBox(wb);
            }
        }
        //导出excel
        String fileName = exportFileName + ".xlsx";
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel"); //输出excel文件
        try {
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("X-SOURCE", "DOWNLOAD");
            OutputStream stream = response.getOutputStream();
            if (null != stream) {
                wb.write(stream);
                wb.close();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归偏移计算
     */
    private int checkCellNum(List<CellRangeAddress> addressList, int cellNum, int rowNum) {
        boolean flag = false;
        for (CellRangeAddress address : addressList) {
            if (address.isInRange(rowNum, cellNum)) {
                cellNum = address.getLastColumn() + 1;
                flag = true;
                break;
            }
        }
        if (flag) {
            cellNum = checkCellNum(addressList, cellNum, rowNum);
        }
        return cellNum;
    }

    /**
     * 转换为前端框架要求的动态表格数据格式
     */
    public TableFrontBase convertTableFront() {
        replaceHead();
        TableFrontBase tableFrontBase = new TableFrontBase();
        //表头
        List<TableFrontHead> headList = new ArrayList<>();
        buildChildren(headList, 0, theadList.get(0).stream().collect(Collectors.summingInt(TableTdBase::getColspan)));
        tableFrontBase.setHeadList(headList);
        //表体
        List<List<String>> bodyList = new ArrayList<>();
        for (List<TableTdBase> row : tbodyList) {
            List<String> cells = new ArrayList<>();
            for (TableTdBase cell : row) {
                cells.add(cell.getValue());
            }
            bodyList.add(cells);
        }
        tableFrontBase.setBodyList(bodyList);
        return tableFrontBase;
    }

    private Map<Integer, Integer> coordinate = new HashMap<>();

    /**
     * 构建表头子节点
     *
     * @param headList 空子节点集合
     * @param index    theadList下标
     * @param colspan  子节点总跨列数
     */
    private void buildChildren(List<TableFrontHead> headList, int index, int colspan) {
        if (!coordinate.containsKey(index)) {
            coordinate.put(index, 0);
        }
        int maxIndex = coordinate.get(index) + colspan;
        for (int i = coordinate.get(index); i < maxIndex; i++) {
            if (index == theadList.size() || i == theadList.get(index).size()) {
                break;
            }
            TableTdBase tdBase = theadList.get(index).get(i);
            TableFrontHead frontHead = new TableFrontHead();
            frontHead.setTitle(tdBase.getValue());
            List<TableFrontHead> children = new ArrayList<>();
            if (isHaveChildren(tdBase, index)) {
                buildChildren(children, index + tdBase.getRowspan(), tdBase.getColspan());
                maxIndex -= tdBase.getColspan() - 1;
            }
            frontHead.setChildren(children);
            headList.add(frontHead);
            coordinate.put(index, coordinate.get(index) + 1);
        }
    }

    /**
     * 判断单元格是否有子集
     */
    private boolean isHaveChildren(TableTdBase tdBase, int index) {
        boolean flag = false;
        if (index < theadList.size() - 1) {//未到最后一行的单元格
            if (tdBase.getColspan() > 1) {//有跨列
                flag = true;
            } else if (tdBase.getRowspan() < theadList.size()) {//无跨列,单元格下一行有空置位
                flag = true;
            }
        }
        return flag;
    }

    private static Map<String, String> replaceMap = new HashMap<>();

    static {
//        replaceMap.put("炸药", "工业炸药(吨)");
    }

    /**
     * 替换表头部分字段显示
     */
    private void replaceHead() {
        for (List<TableTdBase> rowList : theadList) {
            for (TableTdBase cell : rowList) {
                if (replaceMap.containsKey(cell.getValue())) {
                    cell.setValue(replaceMap.get(cell.getValue()));
                }
            }
        }
    }
}
