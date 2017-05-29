package org.apache.poi.api.condition;

public interface CellCondition<T> {

	public T conditionalCell(boolean condition);
	
}
