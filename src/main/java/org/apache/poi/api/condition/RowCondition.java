package org.apache.poi.api.condition;

public interface RowCondition<T> {

	public T nextConditionalRow(boolean condition);
	
}
