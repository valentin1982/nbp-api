package org.apache.poi.fixtures;

import com.google.common.base.Objects;
import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.ss.usermodel.CellStyle;

public class AdditiveStyleTestImpl implements AdditiveStyle {

    private final String id;
    private final StyleType type;

    public AdditiveStyleTestImpl(String id, StyleType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public Enum<?> getType() {
        return type;
    }

    @Override
    public void enrich(WorkbookContext workbookContext, CellStyle style) {
        //do nothing
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AdditiveStyleTestImpl) {
            AdditiveStyleTestImpl that = (AdditiveStyleTestImpl) obj;
            return Objects.equal(this.id, that.id) && Objects.equal(this.type, that.type);
        }
        return false;
    }

    @Override
    public String toString() {
        return id;
    }

}
