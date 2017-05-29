package org.apache.poi.impl.style.defaults;

import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.DateFormatConverter;


import java.util.Locale;

public enum DataStyle implements AdditiveStyle {

	INTEGER("0"),
    AMOUNT("#,##0.00"),
	PERCENTAGE("0.00000%"),

	DATE(DateFormatConverter.convert(Locale.US, "dd-mmm-yyyy")),
	DATE_NUMERIC(DateFormatConverter.convert(Locale.US, "dd-mm-yyyy"));

    private final String format;

    private DataStyle(String format) {
        this.format = format;
    }

	@Override
	public void enrich(WorkbookContext workbookContext, org.apache.poi.ss.usermodel.CellStyle style) {
        DataFormat dataFormat = workbookContext.toNativeWorkbook().createDataFormat();
        style.setDataFormat(dataFormat.getFormat(format));
	}

	@Override
	public Enum<StyleType> getType() {
		return StyleType.DATA;
	}
}