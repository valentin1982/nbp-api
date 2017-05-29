package org.apache.poi.api.sheet;

public interface SheetConfiguration<T> {

	@SuppressWarnings("UnusedReturnValue") // for consistency with other similar methods
	public T setColumnWidth(int columnNumber, int width);

	public T setColumnWidths(int... widths);

	public T hideGrid();

	public T setDefaultRowIndent(int indent);

    public T fitOnPagesByWidth(int pages);

    public T fitOnPagesByHeight(int pages);

}
