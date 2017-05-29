package org.apache.poi.impl.workbook;

import org.apache.poi.api.configuration.Configuration;
import org.apache.poi.api.sheet.SheetContext;
import org.apache.poi.api.style.Style;
import org.apache.poi.api.style.StyleConfiguration;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.impl.sheet.SheetContextImpl;
import org.apache.poi.impl.style.InheritableStyleConfiguration;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class WorkbookContextImpl extends InheritableStyleConfiguration<WorkbookContext> implements WorkbookContext {

    private final Workbook workbook;
    
    private final Map<Style, CellStyle> registeredStyles = new HashMap<Style, CellStyle>();
    
    private final Configuration configuration;
	private final String defaultFontName;

	WorkbookContextImpl(Workbook workbook, StyleConfiguration styleConfiguration, Configuration configuration,
						String defaultFontName) {
    	super(styleConfiguration);
        this.workbook = workbook;
        this.configuration = configuration;
		this.defaultFontName = defaultFontName;
	}
    
    @Override
    public SheetContext createSheet(String sheetName) {
        return new SheetContextImpl(workbook.createSheet(sheetName), this);
    }
    
	@Override
	public SheetContext useSheet(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		checkArgument(sheet != null, "Sheet %s doesn't exist in workbook", sheetName);
		return new SheetContextImpl(sheet, this);
	}    
    
	@Override
	public CellStyle registerStyle(Style style) {
		checkArgument(style != null, "Style is null");

		CellStyle registeredStyle = registeredStyles.get(style);
		
		if (registeredStyle == null) {
			registeredStyle = workbook.createCellStyle();
			style.enrich(this, registeredStyle);
			registeredStyles.put(style, registeredStyle);
		}
		
		return registeredStyle;
	}
    
    @Override
    public byte[] toNativeBytes() {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		workbook.write(baos);
    		return baos.toByteArray();
    	} catch (IOException e) {
    		throw new RuntimeException("Quite unlikely case as we are working with an in-memory data. Wrap to avoid handling checked exception", e);
		}
    }

	@Override
	public String getDefaultFontName() {
		return defaultFontName;
	}

	@Override
	public Workbook toNativeWorkbook() {
		return workbook;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}
}