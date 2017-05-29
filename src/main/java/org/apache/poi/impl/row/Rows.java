package org.apache.poi.impl.row;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import static com.google.common.base.Preconditions.checkNotNull;


public class Rows {
    public static Row getOrCreate(Sheet sheet, int rowNum) {
        Row row = checkNotNull(sheet, "sheet cannot be null").getRow(rowNum);
        if (row == null) {
            return sheet.createRow(rowNum);
        } else {
            return row;
        }
    }
}
