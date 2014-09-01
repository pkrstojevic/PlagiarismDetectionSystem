/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Predrag
 */
@ManagedBean(name = "mainController")
@SessionScoped
public class MainController {

    private String docTitle;
    private String docAuthorName;
    private String docAuthorSurname;
    private Integer docCopyright;
    private Integer docAcademicExpertise;
    private Part file;
    private boolean pushNotification;
    private String statusMessage;
    private String fileContent;
    private List<FacesMessage> msgs;

    public MainController() {
        pushNotification = false;
    }

    public String upload() throws IOException {
        try {
            // Vracam vrednost za prikazivanja poruke
            pushNotification = false;

            // Obrada forme za upload dokumenata
            String fileName = getFileName(file);

            String basePath = Configuration.tempPath;
            File outputFilePath = new File(basePath + fileName);

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
                statusMessage = "File upload failed !!";
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return "uploaded.xhtml";
        } catch (IOException e) {
            return "uploaded.xhtml";
        }
    }

    // Validacija izabranog fajla
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        Part file = (Part) value;
        pushNotification = false;
        msgs = new ArrayList<FacesMessage>();

        if (file.getSize() > 500000000) {
            msgs.add(new FacesMessage("Fajl je veći od 5MB"));
            pushNotification = true;
        }
        if (!file.getContentType().equals("text/plain") && !file.getContentType().equals("application/pdf") && !file.getContentType().equals("application/msword")) {
            msgs.add(new FacesMessage("Niste uneli ispravan format fajla."));
            pushNotification = true;
        }       
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    // Vraca ime fajla
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public List<FacesMessage> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<FacesMessage> msgs) {
        this.msgs = msgs;
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
}
