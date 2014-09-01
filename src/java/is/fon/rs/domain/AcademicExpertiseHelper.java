/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.domain;

import is.fon.rs.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Predrag
 */
public class AcademicExpertiseHelper {
    Session session = null;

    public AcademicExpertiseHelper() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public List getAllAcademicExpertise() {
    List<AcademicExpertise> aeList = null;
    try {
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("FROM AcademicExpertise");
        aeList = (List<AcademicExpertise>) q.list();
    } catch (Exception e) {
        System.err.println(e);
    }
    return aeList;
}
    
    public AcademicExpertise getAcademicExpertise(int academicExpertiseId) {
        AcademicExpertise ae = null;
        try {
            session.beginTransaction();
            ae = (AcademicExpertise) session.get(AcademicExpertise.class, academicExpertiseId);            
        } catch (Exception e) {
            System.err.println(e);
        }
        return ae;
    }     
}
