/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.controller;

import is.fon.rs.domain.Documents;
import is.fon.rs.domain.DocumentsHelper;
import is.fon.rs.pd.FileRead;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Predrag
 */
@ManagedBean
@RequestScoped
public class CheckDocuments {

    @ManagedProperty(value = "#{param.hiddenOrId}")
    int docOriginalId;
    @ManagedProperty(value = "#{param.hiddenPlId}")
    int docCheckId;
    Documents docOriginal;
    Documents docCheck;
    String content1;
    String content2;
    int similarityScore;
    String similarityScorePercentage;
    ArrayList<String> commonKeyWords;

    public CheckDocuments() {
    }

    public void initialization() throws IOException {
        System.out.println(docOriginalId + " " + docCheckId);

        DocumentsHelper dh = new DocumentsHelper();
        docOriginal = dh.getDocument(docOriginalId);
        docCheck = dh.getDocument(docCheckId);
        String path1 = Configuration.databasePath + docOriginal.getFileName();
        String path2 = Configuration.databasePath + docCheck.getFileName();

        content1 = getFileContent(path1);
        content2 = getFileContent(path2);

        FileRead fr = new FileRead();
        ArrayList<String> topWords1 = fr.readMostUsedWord(path1);
        ArrayList<String> topWords2 = fr.readMostUsedWord(path2);

        commonKeyWords = fr.compareTwoLists(topWords1, topWords2);
        HashMap<String, List<String>> hm1 = fr.findWordsFromKeyWord(path1, commonKeyWords, 2);
        HashMap<String, List<String>> hm2 = fr.findWordsFromKeyWord(path2, commonKeyWords, 2);


        similarityScore = fr.compareTwoHashTables(hm1, hm2, commonKeyWords.size());
        similarityScorePercentage = similarityScore + "%";
    }

    public String getFileContent(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String text = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            text += StringUtils.replaceEach(line, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"}) + " <br />";
        }
        return text;
    }

    public ArrayList<String> getCommonKeyWords() {
        return commonKeyWords;
    }

    public int getCountCommonKeyWords() {
        return commonKeyWords.size();
    }
    
    public void setCommonKeyWords(ArrayList<String> commonKeyWords) {
        this.commonKeyWords = commonKeyWords;
    }

    public int getDocOriginalId() {
        return docOriginalId;
    }

    public void setDocOriginalId(int docOriginalId) {
        this.docOriginalId = docOriginalId;
    }

    public int getDocCheckId() {
        return docCheckId;
    }

    public void setDocCheckId(int docCheckId) {
        this.docCheckId = docCheckId;
    }

    public Documents getDocOriginal() {
        return docOriginal;
    }

    public void setDocOriginal(Documents docOriginal) {
        this.docOriginal = docOriginal;
    }

    public Documents getDocCheck() {
        return docCheck;
    }

    public void setDocCheck(Documents docCheck) {
        this.docCheck = docCheck;
    }

    public int getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(int similarityScore) {
        this.similarityScore = similarityScore;
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

    public String getSimilarityScorePercentage() {
        return similarityScorePercentage;
    }

    public void setSimilarityScorePercentage(String similarityScorePercentage) {
        this.similarityScorePercentage = similarityScorePercentage;
    }
}
