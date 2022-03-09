package aletca;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

// Каждый файл в ZIP-файле представлен символом ZipEntry

// boolean hasMoreElements()
//Когда реализован, он обязан вернуть true, пока всё ещё существуют элементы для извлечения,
// и false, когда все элементы были перечислены.

// Object nextElement()
//Возвращает следующий объект в перечислении как общую ссылку Object.

public class FilesParsingTest {

    private static final String
            PDFFILE = "52c8e8114c11cfa9e6e668ee652dbf2b.pdf",
            XLSXFILE = "practice.xlsx",
            CSVFILE = "book.csv";

    @Test
    public void fromFile() throws Exception {
        String pathFile = "src/test/resources/resources.zip";
        ZipFile zipFile = new ZipFile(pathFile);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains("pdf")) {
                assertThat(entry.getName()).isEqualTo(PDFFILE);
                parsePdfTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("xlsx")) {
                assertThat(entry.getName()).isEqualTo(XLSXFILE);
                parseXlsTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("csv")) {
                assertThat(entry.getName()).isEqualTo(CSVFILE);
                parseCsvTest(zipFile.getInputStream(entry));
            }
        }
    }

    void parsePdfTest(InputStream file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains(
                "PDF"
        );
    }

    void parseXlsTest(InputStream file) throws Exception {
        XLS xls = new XLS(file);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(2)
                .getCell(1)
                .getStringCellValue()).contains("Реализация нового дизайна главной страницы");
    }

    void parseCsvTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(3)).contains(
                    "Jenkins.;Stanislav Vasenkov"
            );
        }
    }
}



