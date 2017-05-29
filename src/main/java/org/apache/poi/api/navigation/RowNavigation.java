package org.apache.poi.api.navigation;


import org.apache.poi.api.condition.RowCondition;

public interface RowNavigation<S, R> extends RowCondition<R> {

    public R nextRow();

	public R currentRow();

    public S skipRow();

    public S skipRows(int offset);

    public S stepOneRowBack();
}
