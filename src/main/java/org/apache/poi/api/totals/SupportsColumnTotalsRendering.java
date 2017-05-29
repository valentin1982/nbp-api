package org.apache.poi.api.totals;


import org.apache.poi.api.style.Style;

public interface SupportsColumnTotalsRendering<R> {
    /**
     * The data range will be used to render totals.
     * <b>If the end of data range has not yet been marked, {@link ColumnTotalsDataRange#endOnPreviousRow()}
     * method will be called</b>
     *
     * @param data data range to be used for totals calculation
     * @return this
     */
    R setTotalsDataRange(ColumnTotalsDataRange data);
    R total(Formula formula);
    R total(Formula formula, Style style);
    R totals(Formula formula, int times);
    R totals(Formula formula, int times, Style style);
}
