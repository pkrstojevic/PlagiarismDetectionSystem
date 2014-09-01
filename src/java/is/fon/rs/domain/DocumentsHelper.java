/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.domain;

import is.fon.rs.hibernate.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Predrag
 */
public class DocumentsHelper {

    Session session = null;

    public DocumentsHelper() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public List getAllDocuments() {
        List<Documents> docList = null;
        try {
            session.beginTransaction();
            Query q = session.createQuery("FROM Documents ORDER BY document_id ASC");
            docList = (List<Documents>) q.list();

            for (int i = 0; i < docList.size(); i++) {
                //docList.get(i).setKeywordsList((ArrayList<String>) Arrays.asList(docList.get(i).getKeywords().split("\t")));
                String[] words = docList.get(i).getKeywords().split("\t");
                ArrayList<String> list = new ArrayList<String>(words.length);
                for (String s : words) {
                    list.add(s);
                }
                docList.get(i).setKeywordsList(list);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return docList;
    }
    
    public Documents getDocument(int documentId) {
        Documents doc = null;
        try {
            session.beginTransaction();
            //Query q = session.createQuery("FROM Documents WHERE document_id =" + documentId );
            doc = (Documents) session.get(Documents.class, documentId);
            String[] words = doc.getKeywords().split("\t");
                ArrayList<String> list = new ArrayList<String>(words.length);
                for (String s : words) {
                    list.add(s);
                }
            doc.setKeywordsList(list);
        } catch (Exception e) {
            System.err.println(e);
        }
        return doc;
    }    

    public int insertDocument(Documents doc) {
        int id = 0;
        try {
            session.beginTransaction();
            // Save in database
            session.save(doc);
            //Commit the transaction
            session.getTransaction().commit();
            session.flush();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println(e);
        }
        id = doc.getDocumentId();
        return id;
    }
}
