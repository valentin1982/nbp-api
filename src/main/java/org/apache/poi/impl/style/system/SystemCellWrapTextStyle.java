package org.apache.poi.impl.style.system;

import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.ss.usermodel.CellStyle;


public enum SystemCellWrapTextStyle implements AdditiveStyle {
	
	WRAP_TEXT;
	
	@Override
	public void enrich(WorkbookContext workbookContext, CellStyle style) {
		style.setWrapText(true);
	}

	@Override
	public Enum<SystemStyleType> getType() {
		return SystemStyleType.CELL_WRAP_TEXT;
	}

}