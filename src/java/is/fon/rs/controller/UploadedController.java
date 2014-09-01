package is.fon.rs.controller;

import is.fon.rs.domain.AcademicExpertiseHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import is.fon.rs.domain.Documents;
import is.fon.rs.domain.DocumentsHelper;
import is.fon.rs.pd.Convert2Text;
import is.fon.rs.pd.FileRead;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Predrag
 */
@ManagedBean
@RequestScoped
public class UploadedController {

    private String docTitle;
    private String docAuthorName;
    private String docAuthorSurname;
    private Integer docCopyright;
    private Integer docAcademicExpertise;
    private Documents doc;
    private Date date;
    private final String docFileName;
    private ArrayList<String> topWordsList;
    private String pathToUploadedFile;
    private List<Documents> docSimilarList;

    public List<Documents> getDocSimilarList() {
        return docSimilarList;
    }

    public void setDocSimilarList(List<Documents> docSimilarList) {
        this.docSimilarList = docSimilarList;
    }

    public UploadedController() {
        // Obrada forme
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        docTitle = ec.getRequestParameterMap().get("form:doc_title");
        docAuthorName = ec.getRequestParameterMap().get("form:doc_author_name");
        docAuthorSurname = ec.getRequestParameterMap().get("form:doc_author_surname");
        docCopyright = Integer.parseInt(ec.getRequestParameterMap().get("form:doc_copyright"));
        docAcademicExpertise = Integer.parseInt(ec.getRequestParameterMap().get("form:textbox"));
        docFileName = ec.getRequestParameterMap().get("file_name");
    }

    public void initialization() throws IOException {
        pathToUploadedFile = Configuration.tempPath + docFileName;
        // Konvertujem fajl u txt ekstenziju
        Convert2Text c = new Convert2Text();
        String newFilePath = c.convertToText(pathToUploadedFile);

        // Pronalizimo najučestalije reči u tekstu..
        topWordsList = findMostCommonWords(newFilePath);
        File f = new File(newFilePath);
        date = new Date();

        doc = new Documents();
        doc.setDocumentTitle(docTitle);
        doc.setAcademicExpertiseId(docAcademicExpertise);
        doc.setAuthorFirstname(docAuthorName);
        doc.setAuthorLastname(docAuthorSurname);
        doc.setCopyrightYear(docCopyright);
        doc.setFileName(getFileName(newFilePath));
        doc.setFileSize(String.valueOf(f.length()));
        doc.setUploaded(new Timestamp(date.getTime()));
        doc.setKeywords(arrayListToString(topWordsList));

        DocumentsHelper dh = new DocumentsHelper();
        if (!doc.getFileSize().isEmpty() && doc.getFileSize() != null) {
            doc.setDocumentId(dh.insertDocument(doc));
        }

        docSimilarList = dh.getAllDocuments();
        docSimilarList = getSimilarDocuments(docSimilarList);
        
        AcademicExpertiseHelper aec = new AcademicExpertiseHelper();
        
        for (int i = 0; i < docSimilarList.size(); i++) {
           docSimilarList.get(i).setAcademicExpertise(aec.getAcademicExpertise(docSimilarList.get(i).getAcademicExpertiseId())) ;
        }
    }

    public String getFileName(String inputPath) {
        String outputPath = inputPath.substring(inputPath.lastIndexOf('\\') + 1, inputPath.lastIndexOf('.')) + ".txt";
        return outputPath;
    }

    public ArrayList<String> findMostCommonWords(String filePath) throws IOException {


        FileRead fr = new FileRead();
        ArrayList<String> mostCommonWords = fr.readMostUsedWord(filePath);

        return mostCommonWords;
    }

    public List<Documents> getSimilarDocuments(List<Documents> docList) {
        List<Documents> similarDocList = new ArrayList<Documents>();
        FileRead fr = new FileRead();

        for (int i = 0; i < docList.size()-1; i++) {

            ArrayList<String> commonList = fr.compareTwoLists(topWordsList, docList.get(i).getKeywordsList());
            
            if (commonList.size() > 3) {
                docList.get(i).setCommonKeywordsList(commonList);
                similarDocList.add(docList.get(i));
            }
        }

        return similarDocList;
    }

    public String getFileContent() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String text = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToUploadedFile), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            text += StringUtils.replaceEach(line, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"}) + " <br>";
        }
        return text;
    }

    public String arrayListToString(ArrayList<String> array) {
        ArrayList<String> list = array;
        String listString = "";

        for (String s : list) {
            listString += s + "\t";
        }

        return listString;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocAuthorName() {
        return docAuthorName;
    }

    public void setDocAuthorName(String docAuthorName) {
        this.docAuthorName = docAuthorName;
    }

    public String getDocAuthorSurname() {
        return docAuthorSurname;
    }

    public void setDocAuthorSurname(String docAuthorSurname) {
        this.docAuthorSurname = docAuthorSurname;
    }

    public Integer getDocCopyright() {
        return docCopyright;
    }

    public void setDocCopyright(Integer docCopyright) {
        this.docCopyright = docCopyright;
    }

    public Integer getDocAcademicExpertise() {
        return docAcademicExpertise;
    }

    public void setDocAcademicExpertise(Integer docAcademicExpertise) {
        this.docAcademicExpertise = docAcademicExpertise;
    }

    public Documents getDoc() {
        return doc;
    }

    public void setDoc(Documents doc) {
        this.doc = doc;
    }

    public ArrayList<String> getTopWordsList() {
        return topWordsList;
    }

    public void setTopWordsList(ArrayList<String> topWordsList) {
        this.topWordsList = topWordsList;
    }
}
