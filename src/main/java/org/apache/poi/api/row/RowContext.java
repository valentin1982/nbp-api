package org.apache.poi.api.row;

import org.apache.poi.api.condition.CellCondition;
import org.apache.poi.api.navigation.CellNavigation;
import org.apache.poi.api.navigation.RowNavigation;
import org.apache.poi.api.sheet.SheetContext;
import org.apache.poi.api.style.StyleConfigurable;
import org.apache.poi.api.style.StyleConfiguration;
import org.apache.poi.api.totals.SupportsColumnTotalsRendering;
import org.apache.poi.ss.usermodel.Row;

public interface RowContext extends PlainDataOutput, FormattedDataOutput, CellNavigation<RowContext>, CellCondition<RowContext>,
		RowNavigation<SheetContext, RowContext>, StyleConfiguration, StyleConfigurable<RowContext>,
		SupportsColumnTotalsRendering<RowContext> {

    public RowContext setColumnWidth(int width);

    public RowContext setRowHeight(int height);

    public RowContext mergeCells(int number);

	public Row getNativeRow();
    
}