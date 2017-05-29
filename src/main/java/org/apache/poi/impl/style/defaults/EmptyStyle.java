package org.apache.poi.impl.style.defaults;


import org.apache.poi.api.style.Style;
import org.apache.poi.api.workbook.WorkbookContext;

/**
 * An empty style that does nothing.
 */
public class EmptyStyle implements Style {
    @Override
    public void enrich(WorkbookContext workbookContext, org.apache.poi.ss.usermodel.CellStyle style) {}
    public static final Style instance = new EmptyStyle();
}
