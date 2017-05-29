package org.apache.poi.impl.style.defaults;



import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.api.workbook.WorkbookContext;

import static org.apache.poi.ss.usermodel.CellStyle.*;

public enum CellStyle implements AdditiveStyle {

	BORDERS_THIN_ALL(BORDER_THIN, BORDER_THIN, BORDER_THIN, BORDER_THIN),
	BORDERS_BOTTOM_THIN(BORDER_NONE, BORDER_NONE, BORDER_THIN, BORDER_NONE),
	BORDERS_TOP_BOTTOM_THIN(BORDER_THIN, BORDER_NONE, BORDER_THIN, BORDER_NONE),
	BORDERS_BOTTOM_DOUBLE(BORDER_NONE, BORDER_NONE, BORDER_DOUBLE, BORDER_NONE);

    private final short borderTop;
    private final short borderRight;
    private final short borderBottom;
    private final short borderLeft;

    private CellStyle(short borderTop, short borderRight, short borderBottom, short borderLeft) {
        this.borderTop = borderTop;
        this.borderRight = borderRight;
        this.borderBottom = borderBottom;
        this.borderLeft = borderLeft;
    }

	@Override
	public void enrich(WorkbookContext workbookContext, org.apache.poi.ss.usermodel.CellStyle style) {
		style.setBorderTop(borderTop);
        style.setBorderRight(borderRight);
        style.setBorderBottom(borderBottom);
        style.setBorderLeft(borderLeft);
    }

	@Override
	public Enum<StyleType> getType() {
		return StyleType.CELL;
	}

}
