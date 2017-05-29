package org.apache.poi.api.style;

import com.google.common.collect.ImmutableList;
import org.apache.poi.impl.style.CompositeStyle;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Styles {

    public static AdditiveStyle combine(List<AdditiveStyle> styles) {
        checkNotNull(styles, "styles to combine cannot be null");
        checkArgument(styles.size() > 0, "cannot combine an empty list of styles");

        if (styles.size() == 1) {
            return styles.get(0);
        }

        List<AdditiveStyle> parts = new ArrayList<AdditiveStyle>();
        for (AdditiveStyle style : styles) {
            // can't use polymorphism in this case since we want to keep AdditiveStyle an interface so
            // that it can be implemented by user enums
            if (style instanceof CompositeStyle) {
                parts.addAll(((CompositeStyle) style).getStyles());
            } else {
                parts.add(style);
            }
        }
        return new CompositeStyle(parts);
    }

    public static AdditiveStyle combine(AdditiveStyle... styles) {
        return Styles.combine(ImmutableList.copyOf(styles));
    }
}
