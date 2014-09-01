/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.controller;

import is.fon.rs.domain.AcademicExpertiseHelper;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Predrag
 */
@ManagedBean
@SessionScoped
public class AcademicExpertiseController {

    List academicExpertiseList;
    AcademicExpertiseHelper academicExpertiseHelper;

    public AcademicExpertiseController() {
        academicExpertiseHelper = new AcademicExpertiseHelper();
    }

    public List getAcademicExpertiseList() {
        if (academicExpertiseList == null) {
            academicExpertiseList = academicExpertiseHelper.getAllAcademicExpertise();
        }
        return academicExpertiseList;
    }
}
