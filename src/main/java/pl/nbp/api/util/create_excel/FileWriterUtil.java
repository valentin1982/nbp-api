package pl.nbp.api.util.create_excel;

import com.google.common.io.Files;
import org.apache.poi.api.sheet.SheetContext;
import org.apache.poi.api.workbook.WorkbookContext;
import org.apache.poi.impl.workbook.WorkbookContextFactory;
import pl.nbp.api.domain.Rate;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FileWriterUtil {

    private static WorkbookContextFactory ctxFactory = new WorkbookContextFactory(true);

    public static void writeToFleExel(List<Rate> rates) throws IOException {
        WorkbookContext workbook = render(rates);
        Files.write(workbook.toNativeBytes(), new File(ExcelFileNameUtil.REPORT_XLS));
    }

    private static WorkbookContext render(Collection<Rate> rates) {
        WorkbookContext workbookCtx = ctxFactory.createWorkbook();
        SheetContext sheetCtx = workbookCtx.createSheet("Payments");
        // heading
        sheetCtx
                .nextRow()
                .header("Date").setColumnWidth(15)
                .header("Currency").setColumnWidth(15);
        // row
        for (Rate rate : rates) {
            String date = String.valueOf(rate.getEffectivedate());
            sheetCtx
                    .nextRow()
                    .text(date)
                    .number(rate.getMid());
        }

        return workbookCtx;
    }

}
