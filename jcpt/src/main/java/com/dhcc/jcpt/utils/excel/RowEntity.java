package com.dhcc.jcpt.utils.excel;

import java.util.ArrayList;
import java.util.List;

public class RowEntity {
	
	private int y = 0;
	private List<CellEntity> myCells = new ArrayList<CellEntity>();

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<CellEntity> getMyCells() {
		return myCells;
	}

	public void addCell(CellEntity cellEntity){
		this.myCells.add(cellEntity);
	}
	

}
