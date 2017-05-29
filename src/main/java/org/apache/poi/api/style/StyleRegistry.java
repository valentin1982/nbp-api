package org.apache.poi.api.style;

import org.apache.poi.ss.usermodel.CellStyle;


public interface StyleRegistry {

	public CellStyle registerStyle(Style style);
	
}
