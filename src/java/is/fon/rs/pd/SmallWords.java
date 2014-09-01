/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.pd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Predrag
 */
public class SmallWords {

    public static ArrayList getSmallWords() {
        ArrayList listOfWords = new ArrayList();
        
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new InputStreamReader(new FileInputStream(Config.smallWordsPath), "UTF-8"));

            while ((sCurrentLine = br.readLine()) != null) {
                String[] array = sCurrentLine.split("\n");
                for (int i = 0; i < array.length; i++) {
                    listOfWords.add(array[i]);
                }               
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return listOfWords; 
    }
}
