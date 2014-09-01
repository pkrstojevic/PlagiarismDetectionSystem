/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.pd;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.apache.poi.hwpf.extractor.*;

/**
 *
 * @author Predrag
 */
public class Convert2Text {

    public String convertToText(String inputPath) throws FileNotFoundException, IOException {
        String extension = inputPath.substring(inputPath.lastIndexOf('.') + 1);
        System.out.println(extension);

        if (extension.equals("pdf")) {
            return convertFromPdfToText(inputPath);
        } else if (extension.equals("doc") || extension.equals("docx")) {
            return convertFromDocToText(inputPath);
        } else if (extension.equals("txt")) {
            return convertFromTextToText(inputPath);
        } else {
            return "Format nije podržan";
        }
    }

    // Metoda konvertuje pdf u tekst
    private String convertFromPdfToText(String inputPath) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhmmss");
        String formattedDate = sdf.format(date);

        String output = Config.databasePath + formattedDate + ".txt";

        System.out.println(output);
        StringBuffer text = new StringBuffer();
        String resultText = "";
        PdfReader reader;
        try {
            reader = new PdfReader(inputPath);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            PrintWriter out = new PrintWriter(new FileOutputStream(output));
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                text.append(strategy.getResultantText());

            }
            resultText = text.toString();
            resultText = resultText.replaceAll("-\n", "");
            out.println(resultText);

            StringTokenizer stringTokenizer = new StringTokenizer(resultText, "\n");
            PrintWriter lineWriter = new PrintWriter(new FileOutputStream(output));
            while (stringTokenizer.hasMoreTokens()) {
                String curToken = stringTokenizer.nextToken();
                lineWriter.println(curToken);
            }
            lineWriter.flush();
            lineWriter.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return output;

    }

    // Metoda konvertuje doc u tekst
    private String convertFromDocToText(String inputPath) throws FileNotFoundException, FileNotFoundException, FileNotFoundException, FileNotFoundException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhmmss");
        String formattedDate = sdf.format(date);

        String output = Config.databasePath + formattedDate + ".txt";

        String filesname = inputPath;
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(filesname));
            //Couldn't close the braces at the end as my site did not allow it to close

            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            String[] paragraphs = we.getParagraphText();


            PrintWriter lineWriter = new PrintWriter(new FileOutputStream(output));
            System.out.println("Word Document has " + paragraphs.length + " paragraphs");
            for (int i = 0; i < paragraphs.length; i++) {

                paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
                String text = we.stripFields(paragraphs[i]);
                System.out.println("Pragraf-->" + text);
                lineWriter.println(text);
            }
            lineWriter.flush();
            lineWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    private String convertFromTextToText(String inputPath) throws FileNotFoundException, FileNotFoundException, FileNotFoundException, FileNotFoundException, IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhmmss");
        String formattedDate = sdf.format(date);

        String output = Config.databasePath + formattedDate + ".txt";

        File afile = new File(inputPath);
        File bfile = new File(output);

        InputStream inStream = new FileInputStream(afile);
        OutputStream outStream = new FileOutputStream(bfile);

        byte[] buffer = new byte[1024];

        int length;
        //copy the file content in bytes 
        while ((length = inStream.read(buffer)) > 0) {

            outStream.write(buffer, 0, length);

        }

        inStream.close();
        outStream.close();

        System.out.println("File is copied successful!");
        System.out.println(output);
        return output;
    }
}
