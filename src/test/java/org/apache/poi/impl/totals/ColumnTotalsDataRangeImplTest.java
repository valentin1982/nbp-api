package org.apache.poi.impl.totals;

import org.apache.poi.impl.sheet.SheetContextImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColumnTotalsDataRangeImplTest {

    private SheetContextImpl sheetContext = mock(SheetContextImpl.class);


    @Test(expected = IllegalStateException.class)
    public void testEndCalledTwice_illegalState() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);
        range.endOnCurrentRow();
        range.endOnCurrentRow();
    }

    @Test
    public void testInstanceCreated_firstRowTakenFromSheetContextAndIs1Based() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);

        // verify
        assertEquals(3, range.getStartRowNo());
    }

    @Test
    public void testEndOnPreviousRow_itIsTakenFromSheetContext() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1, 10);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);
        range.endOnPreviousRow();

        // verify
        assertEquals(10, range.getEndRowNo());
    }

    @Test
    public void testEndOfCurrentRow_itIsTakenFromSheetContext() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1, 10);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);
        range.endOnCurrentRow();

        // verify
        assertEquals(11, range.getEndRowNo());
    }

    @Test (expected = IllegalStateException.class)
    public void testEndCalledOnTheSameRowWhereStartIs_illegalState() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);
        range.endOnCurrentRow();
    }

    @Test
    public void testEndOnCurrentRowCalledOnSameRowWhereStartIs_endEqualsToStart() {
        // given
        when(sheetContext.getCurrentRowNo()).thenReturn(1, 2);

        // do
        ColumnTotalsDataRangeImpl range = new ColumnTotalsDataRangeImpl(sheetContext);
        range.endOnCurrentRow();

        // verify
        assertEquals(range.getStartRowNo(), range.getEndRowNo());
    }
}
