package org.apache.poi.impl.totals;

import com.google.common.base.Objects;
import org.apache.poi.api.totals.ColumnTotalsDataRange;
import org.apache.poi.impl.sheet.SheetContextImpl;


import static com.google.common.base.Preconditions.checkState;


public class ColumnTotalsDataRangeImpl implements ColumnTotalsDataRange {

    private final static int NOT_SET = -1;

    private final SheetContextImpl sheetContext;
    private final int startRowNo;
    private int endRowNo = NOT_SET;

    public ColumnTotalsDataRangeImpl(SheetContextImpl sheetContext) {
        this.startRowNo = sheetContext.getCurrentRowNo() + 2; // +1 since it's next row, +1 since line numbers are
                                                              // 0-based in SheetContextImpl
        this.sheetContext = sheetContext;
    }

    @Override
    public void endOnCurrentRow() {
        endOn(0);
    }

    @Override
    public void endOnPreviousRow() {
        endOn(-1);
    }

    @Override
    public void endOn(int rowOffset) {
        end(sheetContext.getCurrentRowNo() + rowOffset);
    }

    private void end(int onLine) {
        checkState(endRowNo == NOT_SET, "Don't mark range end twice. End line was already marked.", this);
        endRowNo = onLine + 1; // +1 since line numbers are 0-based in SheetContextImpl
        checkState(endRowNo >= startRowNo, "No data for totals.", this);
    }

    @Override
    public int getStartRowNo() {
        return startRowNo;
    }

    @Override
    public int getEndRowNo() {
        checkState(isEndMarked(), "End line of data range must be marked before trying to retrieve it.", this);
        return endRowNo;
    }

    @Override
    public boolean isEndMarked() {
        return endRowNo != NOT_SET;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("startRow", startRowNo)
                .add("endRow", endRowNo)
                .add("isEndMarked", isEndMarked())
                .toString();
    }
}
