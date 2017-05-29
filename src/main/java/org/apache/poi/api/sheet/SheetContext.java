package org.apache.poi.api.sheet;

import org.apache.poi.api.condition.BlockCondition;
import org.apache.poi.api.configuration.ConfigurationProvider;
import org.apache.poi.api.navigation.RowNavigation;
import org.apache.poi.api.row.RowContext;
import org.apache.poi.api.style.StyleConfigurable;
import org.apache.poi.api.style.StyleConfiguration;
import org.apache.poi.api.totals.ColumnTotalsDataRangeSource;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetContext extends RowNavigation<SheetContext, RowContext>, BlockCondition<SheetContext>,
		SheetConfiguration<SheetContext>, ConfigurationProvider, StyleConfiguration, StyleConfigurable<SheetContext>, ColumnTotalsDataRangeSource {

	@SuppressWarnings("UnusedReturnValue") // for consistency with other similar methods
	public SheetContext mergeCells(int startColumn, int endColumn);

	public Sheet getNativeSheet();
  
}