package org.apache.poi.impl.style;

import com.google.common.collect.ImmutableList;
import org.apache.poi.api.style.AdditiveStyle;
import org.apache.poi.api.style.Style;
import org.apache.poi.api.style.Styles;


import static com.google.common.base.Preconditions.checkNotNull;

public class StylesInternal {
    /**
     * @param style1 not-null
     * @param style2 not-null
     * @return if at least one style given is non-additive, second passed style is returned (it 'overrides' the 1st).
     * Otherwise, styles are combined and a composite style is returned.
     */
    public static Style combineOrOverride(Style style1, Style style2) {
        checkNotNull(style1, "first style to combine cannot be null");
        checkNotNull(style2, "second style to combine cannot be null");

        if (!(style1 instanceof AdditiveStyle) || !(style2 instanceof AdditiveStyle)) {
            return style2;
        }
        return Styles.combine(ImmutableList.of((AdditiveStyle) style1, (AdditiveStyle) style2));
    }
}
