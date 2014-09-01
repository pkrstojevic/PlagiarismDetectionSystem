package is.fon.rs.pd;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Predrag
 */
public class Config {
    public static int numberFrequentWords = 10;
    
    private static final String projectPath = "C:\\Users\\Predrag\\Documents\\NetBeansProjects\\PDS_WebApp";
    
    // Stop-words url
    public static String smallWordsPath = projectPath + "\\data\\SmallWords.txt"; 
    public static String tempPath = projectPath + "\\temp\\";
    public static String databasePath = projectPath + "\\database\\";
}
