package org.apache.poi.api.row;

import com.google.common.base.Optional;

public interface FormattedDataOutput {

    public RowContext header(String text);

    public RowContext total(String text);

    public RowContext percentage(Number number);

    public RowContext percentage(Optional<? extends Number> number);

}
