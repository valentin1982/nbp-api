package org.apache.poi.api.row;

import com.google.common.base.Optional;
import org.apache.poi.api.style.Style;


import java.util.Collection;
import java.util.Date;

public interface PlainDataOutput {

    public RowContext text(String text);

    public RowContext text(String text, Style style);

    public RowContext text(Optional<String> text);

    public RowContext text(Optional<String> text, Style style);

    public RowContext multilineText(Collection<String> lines);

    public RowContext multilineText(Collection<String> lines, Style style);

    public RowContext number(Number number);

    public RowContext number(Number number, Style style);

    public RowContext number(Optional<? extends Number> number);

    public RowContext number(Optional<? extends Number> number, Style style);

    public RowContext date(Date date);

    public RowContext date(Date date, Style style);

    public RowContext date(Optional<Date> date);

    public RowContext date(Optional<Date> date, Style style);
}
