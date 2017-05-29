package org.apache.poi.api.style;

import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.ss.usermodel.CellStyle;

public interface Style {

    public void enrich(WorkbookContext workbookContext, CellStyle style);
}