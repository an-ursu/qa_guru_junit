package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipFileTests {

    private ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    @DisplayName("PDF файл содержит текст про Kubernetes ")
    void checkPdfFileInZipFileContainsText() throws Exception {

        boolean pdfFileInZip = false;

        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("hw.zip"))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("book.pdf")) {
                    pdfFileInZip = true;
                    assertNotNull(entry);
                    byte[] pdfRead = zis.readAllBytes();
                    assertTrue(pdfRead.length > 0);
                    PDF pdf = new PDF(pdfRead);
                    String pdfText = pdf.text;
                    Assertions.assertTrue(pdfText.contains("Путеводитель по Kubernetes"), "В 'book.pdf' отсутствует текст 'Путеводитель по Kubernetes'.");
                }
            }
            assertTrue(pdfFileInZip, "Файл 'book.pdf' отсутствует в 'hw.zip'.");

        }

    }

    @Test
    @DisplayName("XLSX файл содержит 'пункт погрузки'.")
    void checkXlsxFileInZipFileContainsText() throws Exception {
        boolean xlsxFileInZip = false;

        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("hw.zip"))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("info_drivers.xlsx")) {
                    xlsxFileInZip = true;
                    assertNotNull(entry);
                    byte[] xlsxRead = zis.readAllBytes();
                    assertTrue(xlsxRead.length > 0);
                    XLS xlsx = new XLS(xlsxRead);

                    String xlsxText = xlsx.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    Assertions.assertTrue(xlsxText.contains("пункт погрузки"));
                }

            }
            assertTrue(xlsxFileInZip, "Файл 'info_drivers.xlsx' отсутствует в 'hw.zip'.");
        }

    }


    @Test
    @DisplayName("Проверяем, что CSV в ZIP содержит 'Москва'")
    void csvInZipContainsMoscow() throws Exception {

        try (ZipInputStream zis = new ZipInputStream(
                getClass().getClassLoader().getResourceAsStream("hw.zip"))) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if ("info.csv".equals(entry.getName())) {
                    try (CSVReader reader = new CSVReader(
                            new InputStreamReader(zis, StandardCharsets.UTF_8))) {

                        List<String[]> data = reader.readAll();
                        assertFalse(data.isEmpty(), "CSV файл не должен быть пустым");
                        boolean hasMoscow = false;
                        for (String[] row : data) {
                            for (String cell : row) {
                                if (cell != null &&
                                        cell.replace("\uFEFF", "").contains("Москва")) {
                                    hasMoscow = true;
                                    break;
                                }
                            }
                            if (hasMoscow) break;
                        }
                        assertTrue(hasMoscow, "CSV должен содержать 'Москва'");
                        return;
                    }
                }
            }
            fail("Файл info.csv не найден в архиве hw.zip");
        }
    }
}

