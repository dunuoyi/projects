package com.dhcc.jcpt.utils.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelEntity {
	
	private List<RowEntity> myRows = new ArrayList<RowEntity>();

	public List<RowEntity> getMyRows() {
		return myRows;
	}
	public void addRow(RowEntity rowEntity){
		this.myRows.add(rowEntity);
	}
	/**
	 * 获取一共有多少列小单元格
	 * @return
	 */
	public int getUnitCellNum(){
		int unitCellNum = 0;
		for(RowEntity rowEntity:myRows){
			if(rowEntity.getMyCells()!=null&&unitCellNum<rowEntity.getMyCells().size()){
				unitCellNum = rowEntity.getMyCells().size();
			}
		}
		return unitCellNum;
	}
	/**
	 * 获取一共多少行
	 * @return
	 */
	public int getUnitRowNum(){
		return myRows.size();
	}

}
