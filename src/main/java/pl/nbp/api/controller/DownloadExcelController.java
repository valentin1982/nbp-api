package pl.nbp.api.controller;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.nbp.api.util.create_excel.ExcelFileNameUtil;
import pl.nbp.api.util.date.DateHelperUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

@Controller
public class DownloadExcelController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DownloadExcelController.class);

    private FileInputStream file;

    /**
     *Download excel from<i>api.nbe.pl</i> service.
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadXLS")
    public void downloadXLS(HttpServletResponse response) throws IOException {
        long lStartTime = getlStartTime();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + ExcelFileNameUtil.REPORT_XLS);
            file = new FileInputStream(new File(ExcelFileNameUtil.REPORT_XLS));
            logger.info(file);
            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            logger.info(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            logger.info(DateHelperUtil.LOCAL_DATE + " EffectiveDate = " + cell.getNumericCellValue() + "\t\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            logger.info(DateHelperUtil.LOCAL_DATE + " Mid " + cell.getStringCellValue() + "\t\t");
                            break;
                    }
                }
                System.out.println("");
                workbook.write(response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (FileNotFoundException e) {
            logger.error("DownloadExcelController FileNotFoundException : " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("DownloadExcelController IOException : " + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            file.close();
        }
        long result = getEndTime(lStartTime);
        logger.info("downloadXLS(); in seconds: " + result);
    }


    private long getEndTime(long lStartTime) {
        long lEndTime = getlStartTime();
        long output = lEndTime - lStartTime;
        return output;
    }

    private long getlStartTime() {
        return new Date().getTime() / 1000;
    }

}
