package com.dhcc.jcpt.utils.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * 生成excel文件工具类
 * @author dujl
 *
 */
public class ExcelUtil {
	
	public static void main(String[] args) {
		ExcelEntity entity = new ExcelEntity();
		//设置标题
		RowEntity rowEntity0 = new RowEntity();
		rowEntity0.setY(0);
		CellEntity cellEntity00 = new CellEntity();
		cellEntity00.setX(0);
		cellEntity00.setText("序号");
		cellEntity00.setHeight(2);
		rowEntity0.addCell(cellEntity00);
		CellEntity cellEntity01 = new CellEntity();
		cellEntity01.setX(1);
		cellEntity01.setText("省份");
		cellEntity01.setHeight(2);
		rowEntity0.addCell(cellEntity01);
		CellEntity cellEntity02 = new CellEntity();
		cellEntity02.setX(2);
		cellEntity02.setText("其他交易类型");
		cellEntity02.setWidth(2);
		rowEntity0.addCell(cellEntity02);
		CellEntity cellEntity04 = new CellEntity();
		cellEntity04.setX(4);
		cellEntity04.setText("是否得分（得分1，不得分0）");
		cellEntity04.setHeight(2);
		rowEntity0.addCell(cellEntity04);
		CellEntity cellEntity05 = new CellEntity();
		cellEntity05.setX(5);
		cellEntity05.setText("总计");
		cellEntity05.setHeight(2);
		rowEntity0.addCell(cellEntity05);
		entity.addRow(rowEntity0);
		
		RowEntity rowEntity1 = new RowEntity();
		rowEntity1.setY(1);
		CellEntity cellEntity12 = new CellEntity();
		cellEntity12.setX(2);
		cellEntity12.setText("四大领域");
		rowEntity1.addCell(cellEntity12);
		CellEntity cellEntity13 = new CellEntity();
		cellEntity13.setX(3);
		cellEntity13.setText("医疗");
		rowEntity1.addCell(cellEntity13);
		entity.addRow(rowEntity1);
		
		String excelPath = "D:\\temp\\bbb.xls";
		for (int x = 2; x < 6; x++) {
			RowEntity rowEntity = new RowEntity();
			rowEntity.setY(x);
			entity.addRow(rowEntity);
			for (int y = 0; y < 6; y++) {
				CellEntity cellEntity = new CellEntity();
				cellEntity.setX(y);
				cellEntity.setText("daaaaa" + y);
				rowEntity.addCell(cellEntity);
			}
		}
		try {
			createExcel(entity,excelPath,0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("创建excel完成！");
		/*
		excel文件输出到页面下载
		response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(currentDate+"其它交易类型.xls", "UTF-8"));
        response.setCharacterEncoding("gbk");
        toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        book.write(toClient);
        toClient.flush();
        toClient.close();
		 */
		
		
		
	}
	/**
	 * 输出excel输出到指定路径
	 * @param entity
	 * @param excelPath
	 * @param sheetIndex
	 * @throws IOException 
	 */
	public static void createExcel(ExcelEntity entity,String excelPath,int sheetIndex) throws IOException {
		OutputStream toClient = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(excelPath);
			HSSFWorkbook book = createWorkbook(entity,sheetIndex);
			toClient = new BufferedOutputStream(os);
			book.write(toClient);
			toClient.flush();
		} finally {
			if(toClient!=null)
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 赋值，设置样式
	 * @param entity
	 * @param sheetIndex
	 * @return
	 * @throws IOException
	 */
	public static HSSFWorkbook createWorkbook(ExcelEntity entity,int sheetIndex) throws IOException {
		HSSFWorkbook book = getWorkbook();// 获取excel模版
		HSSFSheet sheet = book.getSheetAt(sheetIndex);
		CellStyle cellstyle = createMyCellStyle(book);
		for(int y=0;y<entity.getUnitRowNum();y++){//设置样式
			HSSFRow row0 = sheet.createRow(y);
			System.out.println(entity.getUnitCellNum());
			for(int x = 0;x<entity.getUnitCellNum();x++){
				HSSFCell cell0 = row0.createCell(x);
				cell0.setCellStyle(cellstyle);//设置样式
			}
		}
		for (RowEntity rowEntity : entity.getMyRows()) {//遍历行
			HSSFRow row = sheet.getRow(rowEntity.getY());//根据行号创建行
			row.setHeight((short) 500);//设置行高
			for (CellEntity cellEntity : rowEntity.getMyCells()) {//遍历每个单元格
				if(cellEntity.getWidth()>1||cellEntity.getHeight()>1){//根据设定，进行单元格合并
					CellRangeAddress cra=new CellRangeAddress( rowEntity.getY(), rowEntity.getY()+cellEntity.getHeight()-1
												,cellEntity.getX(), cellEntity.getX()+cellEntity.getWidth()-1); 
					sheet.addMergedRegion(cra);//合并
				}
				HSSFCell cell = row.getCell(cellEntity.getX());//根据单元号，创建单元
				cell.setCellValue(cellEntity.getText());//赋值
			}
		}
		return book;
	}
	/**
	 * 创建单元格样式
	 * @param book
	 * @return
	 */
	public static CellStyle createMyCellStyle(HSSFWorkbook book) {
		CellStyle cellstyle = book.createCellStyle();
		cellstyle.setBorderBottom(BorderStyle.THIN);//设置边框
		cellstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellstyle.setBorderLeft(BorderStyle.THIN);
		cellstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellstyle.setBorderRight(BorderStyle.THIN);
		cellstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());//边框颜色
		cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		cellstyle.setAlignment(HorizontalAlignment.CENTER);// 水平
		return cellstyle;
	}
	/**
	 * 获取模版，生成Workbook对象
	 * @return
	 * @throws IOException
	 */
	public static HSSFWorkbook getWorkbook() throws IOException{
		BufferedInputStream in = null;
		InputStream is = null;
		HSSFWorkbook book = null;
		try {
			//获取模版
			is = ExcelUtil.class.getResourceAsStream("/com/dhcc/utils/excel/标准模版.xls");
			in = new BufferedInputStream(is);
			// 打开HSSFWorkbook
			POIFSFileSystem fs = new POIFSFileSystem(in);
			book = new HSSFWorkbook(fs);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return book;
	}

}
