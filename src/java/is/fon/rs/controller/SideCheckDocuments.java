/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.controller;

import is.fon.rs.pd.FileRead;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Predrag
 */
@ManagedBean
@RequestScoped
public class SideCheckDocuments {

    private Part file;
    private Part file2;
    private String content1;
    private String content2;
    private ArrayList<String> commonKeyWords;
    private ArrayList<String> topWords1;
    private ArrayList<String> topWords2;
    private int similarityScore;
    private String similarityScorePercentage;
    private boolean isCompleted = false;


    public void upload() throws IOException {
        String path1 = uploadFile(file, Configuration.tempPath);
        String path2 = uploadFile(file2, Configuration.tempPath);

        content1 = getFileContent(path1);
        content2 = getFileContent(path2);

        FileRead fr = new FileRead();
        topWords1 = fr.readMostUsedWord(path1);
        topWords2 = fr.readMostUsedWord(path2);

        commonKeyWords = fr.compareTwoLists(topWords1, topWords2);
        HashMap<String, List<String>> hm1 = fr.findWordsFromKeyWord(path1, commonKeyWords, 2);
        HashMap<String, List<String>> hm2 = fr.findWordsFromKeyWord(path2, commonKeyWords, 2);


        similarityScore = fr.compareTwoHashTables(hm1, hm2, commonKeyWords.size());
        similarityScorePercentage = similarityScore + "%";
        isCompleted = true;
    }

    public SideCheckDocuments() {
    }

    private String uploadFile(Part file, String path) throws IOException {
        // Obrada forme za upload dokumenata
        String fileName = getFileName(file);
        System.out.println("FileName: " + fileName);

        String basePath = path;
        String filePath = basePath + fileName;
        File outputFilePath = new File(filePath);

        // Copy uploaded file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return filePath;
    }

    public String getFileContent(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String text = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            text += StringUtils.replaceEach(line, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"}) + " <br>";
        }
        return text;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public ArrayList<String> getCommonKeyWords() {
        return commonKeyWords;
    }

    public void setCommonKeyWords(ArrayList<String> commonKeyWords) {
        this.commonKeyWords = commonKeyWords;
    }

    public int getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(int similarityScore) {
        this.similarityScore = similarityScore;
    }

    public String getSimilarityScorePercentage() {
        return similarityScorePercentage;
    }

    public void setSimilarityScorePercentage(String similarityScorePercentage) {
        this.similarityScorePercentage = similarityScorePercentage;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile2() {
        return file2;
    }

    public void setFile2(Part file2) {
        this.file2 = file2;
    }
    
    public ArrayList<String> getTopWords1() {
        return topWords1;
    }

    public void setTopWords1(ArrayList<String> topWords1) {
        this.topWords1 = topWords1;
    }

    public ArrayList<String> getTopWords2() {
        return topWords2;
    }

    public void setTopWords2(ArrayList<String> topWords2) {
        this.topWords2 = topWords2;
    }
    
    public int getCountCommonKeyWords() {
        return commonKeyWords.size();
    }    
}
