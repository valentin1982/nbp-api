package org.apache.poi.impl.column;

import org.junit.Test;

import static org.apache.poi.impl.column.Columns.columnIndexAsLetters;
import static org.junit.Assert.assertEquals;


public class ColumnsTest {
    @Test
    public void testColumnIndexAsLetters() {
        assertEquals("A", columnIndexAsLetters(1));
        assertEquals("B", columnIndexAsLetters(2));
        assertEquals("Z", columnIndexAsLetters(26));
        assertEquals("AA", columnIndexAsLetters(27));
        assertEquals("IV", columnIndexAsLetters(256));
        assertEquals("ZZ", columnIndexAsLetters(702));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testColumnIndexExceedsMaxLimit() {
        columnIndexAsLetters(703);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testColumnIndexIsNotPositive() {
        columnIndexAsLetters(0);
    }
}
