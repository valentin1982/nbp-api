package org.apache.poi.impl.style;

import com.google.common.collect.ImmutableList;
import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.fixtures.AdditiveStyleTestImpl;
import org.apache.poi.fixtures.StyleType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompositeStyleTest {
    private AdditiveStyle style1type1 = new AdditiveStyleTestImpl("style1", StyleType.type1);

    @Test
    public void testEqualsContract() {
        // given
        CompositeStyle composite1 = new CompositeStyle(ImmutableList.of(style1type1));
        CompositeStyle composite2 = new CompositeStyle(ImmutableList.of(style1type1));

        // verify
        assertEquals(composite1, composite2);
    }
}
