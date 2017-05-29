package org.apache.poi.api.navigation;

public interface CellNavigation<T> {

    public T skipCell();

    public T skipCells(int offset);

    public T cellAt(int newIndex);

}
