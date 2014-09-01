/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.pd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Predrag
 */
public class CreateReport {

    public void writeReport(String numberOfKeyWords, int numberOfWordsInPhrase, String percentageOfMatches, String fileName1, String fileName2) throws IOException {
        File file = new File("temp/Report.txt");        
        
        
        // Ulazni parametar true, oznacava da ostavi tekst koji se nalazi u fajlu..
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);       


        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        
        bw.write("------------------------------------------------------------------------");
        bw.newLine();
        bw.write("| Original: \t" + fileName1.substring(fileName1.lastIndexOf('\\')+1, fileName1.length()) + " |");
        bw.newLine();
        bw.write("| Modified: \t" + fileName2.substring(fileName2.lastIndexOf('\\')+1, fileName2.length()) + " |");
        bw.newLine();        
        bw.write("------------------------------------------------------------------------");
        bw.newLine();
        bw.write("Number of key words: \t\t" + numberOfKeyWords);
        bw.newLine();
        bw.write("Number of words in phrase: \t" + numberOfWordsInPhrase);
        bw.newLine();
        bw.write("Percentage of matches: \t\t" + percentageOfMatches + "%");
        bw.newLine();       
        bw.write("------------------------------------------------------------------------");
        bw.newLine();
        bw.newLine();
        bw.newLine();  
        bw.close();

        System.out.println("Done");        
    }
    
    
    public void writeWordsReport(ArrayList<String> topWords, ArrayList<Integer> topWordsCount, String path) throws IOException {
        File file = new File("temp/Word Report.txt");        
                
        // Ulazni parametar true, oznacava da ostavi tekst koji se nalazi u fajlu..
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);       


        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        
        bw.write("------------------------------------------------------------------------");
        bw.newLine();
        bw.write("| Original: \t" + path.substring(path.lastIndexOf('\\')+1, path.length()) + " |");
        bw.newLine();
        bw.write("------------------------------------------------------------------------");
        bw.newLine();
        bw.write("\tTOP WORDS \t\t|\t\t COUNT");
        bw.newLine();
        bw.write("------------------------------------------------------------------------");
        for (int i = 0; i < topWords.size(); i++) {
            bw.newLine();
            bw.write((i+1) + "." + "\t" + topWords.get(i) + "\t\t\t\t" + topWordsCount.get(i));            
        }
        bw.newLine();
        bw.newLine();
        bw.close();
    }
}
