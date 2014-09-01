/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.pd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Predrag
 */
public class FileRead {

    BufferedReader br = null;
    ArrayList<String> allWords = null;
    

    // Vraca listu najkoriscenijih reci u tekstu
    public ArrayList<String> readMostUsedWord(String path) throws IOException {
        Map<String, Word> countMap = new HashMap<String, Word>();
        try {
            String sCurrentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            while ((sCurrentLine = br.readLine()) != null) {
                // Regularni izraz koji je nekada stajao: "[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+"
                String[] words = sCurrentLine.split("[^A-ZČĆĐŠŽa-zčćđšž]+");

                //Radi brzine algoritma, učitavamo u memoriju reči koje se izbacuju po defaultu
                ArrayList listOfWords = SmallWords.getSmallWords();
                for (String wordLabel : words) {

                    // Ne uključuje reči koje su manje ili jednake od dva slova, izacujem određene reči 
                    if (listOfWords.contains(wordLabel)
                            || wordLabel.length() <= 1
                            || "".equals(wordLabel)) {
                        continue;
                    }

                    Word word = countMap.get(wordLabel.toLowerCase());
                    if (word == null) {
                        //Prebacujem sve u mala slova, da ne bi dolazilo do razlika
                        word = new Word(wordLabel.toLowerCase(), 0);
                        countMap.put(wordLabel, word);
                    }

                    word.incrementCount();
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

        SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
        System.out.println("*** Broj reci u listi je: " + sortedWords.size());
        // Lista koja treba da vrati najkoriscenije reci
        ArrayList<String> topWords = new ArrayList<String>();
        ArrayList<Integer> topWordsCount = new ArrayList<Integer>();
        
        //CreateReport cr = new CreateReport();
        
        int i = 0;
        for (Word word : sortedWords) {
            // Vraca top reci koje su koriscene, definisane Config fajlom
            if (i > Config.numberFrequentWords) {
                break;
            }
            topWords.add(word.getWord());
            topWordsCount.add(word.getCount());
            System.out.println(i + "\t " + word.getWord() + " " + word.getCount());
            i++;
        }
        
        // Kreiram report..
        //cr.writeWordsReport(topWords, topWordsCount, path);
        return topWords;
    }

    public ArrayList<String> splitFileOnWords(String path) {
        ArrayList<String> words = new ArrayList<String>();

        try {
            String sCurrentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            while ((sCurrentLine = br.readLine()) != null) {
                // Regularni izraz koji je nekada stajao: "[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+"
                String[] wordsArray = sCurrentLine.split("[^A-ZČĆĐŠŽa-zčćđšž]+");

                // Ubacujem u listu splitovane reci
                for (int i = 0; i < wordsArray.length; i++) {
                    if (!wordsArray[i].equals("") && !wordsArray[i].equals(" ")) {
                        //.replaceAll("\\s+","") znaci uklanjanje svih whitespaceova
                        words.add(wordsArray[i].replaceAll("\\s+",""));
                    }
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
        return words;
    }

    // Vraca rec pre i posle zadate reci
    public HashMap findWordsFromKeyWord(String path, ArrayList<String> list, int numberOfWordInPhrase) {
        // Služi da dobijemo samo reči koje se poklapaju, da bi smanjio proces pretrage
        ArrayList<String> topWords = list;
        ArrayList<String> words = splitFileOnWords(path);

        // U hash tabeli čuvamo ključnu reč i sve ono što pronađem u tekstu + prethodna reč + reč posle
        Map mMap = new HashMap();

        for (int i = 0; i < topWords.size(); i++) {

            // Ispisujem koja reč je na redu
            System.out.println("-----------> " + topWords.get(i) + " <-----------");
            // Punimo u listu sve reci koje smo pronasli
            allWords = new ArrayList<String>();
            allWords.clear();

            int j = 0;
            //Ako linija sadrži ključnu reč         
            for (String word : words) {
                if (word.equals(topWords.get(i))) {
                    try {
                        if(numberOfWordInPhrase == 2) {
                        String setOfWords = words.get(j - 1).toLowerCase() + " " + word.toLowerCase();
                        String setOfWords2 = word.toLowerCase() + " " + words.get(j + 1).toLowerCase();
                            //Ako skup reci vec postoji u listi, ne treba ubacivati
                            if (!allWords.contains(setOfWords)) {
                                allWords.add(setOfWords);

                            }

                            if (!allWords.contains(setOfWords2)) {
                                allWords.add(setOfWords2);

                            }                         
                        }
                        
                        else if(numberOfWordInPhrase == 3) {
                        String setOfWords = words.get(j - 1).toLowerCase() + " " + word.toLowerCase() + " " + words.get(j + 1).toLowerCase();
                            //Ako skup reci vec postoji u listi, ne treba ubacivati
                            if (!allWords.contains(setOfWords)) {
                                allWords.add(setOfWords);
                            }                        
                        }
                        else if(numberOfWordInPhrase == 4) {
                        String setOfWords = words.get(j - 1).toLowerCase() + " " + word.toLowerCase();
                        String setOfWords2 = word.toLowerCase() + " " + words.get(j + 1).toLowerCase();
                        String setOfWords3 = words.get(j - 2).toLowerCase() + " " + words.get(j - 1).toLowerCase();
                        String setOfWords4 = words.get(j + 2).toLowerCase() + " " + words.get(j + 1).toLowerCase();                        
                            //Ako skup reci vec postoji u listi, ne treba ubacivati
                            if (!allWords.contains(setOfWords)) {
                                allWords.add(setOfWords);
                            }

                            if (!allWords.contains(setOfWords2)) {
                                allWords.add(setOfWords2);
                            }
                            
                            if (!allWords.contains(setOfWords3)) {
                                allWords.add(setOfWords3);
                            }
                            
                            if (!allWords.contains(setOfWords4)) {
                                allWords.add(setOfWords4);
                            }                            
                        }
                        else {
                        String setOfWords = words.get(j - 2).toLowerCase() + " " + 
                                words.get(j - 1).toLowerCase() + " " + 
                                word.toLowerCase() + " " + 
                                words.get(j + 1).toLowerCase() + " " + 
                                words.get(j + 2).toLowerCase();
                            //Ako skup reci vec postoji u listi, ne treba ubacivati
                            if (!allWords.contains(setOfWords)) {
                                allWords.add(setOfWords);
                            }                         
                        }
                           
                    } catch (Exception e) {
                    }
                }
                j++;
            }

            //System.err.println(allWords.toString());
            // Ubacujem dobijene rezultate u hasmapu                    
            if (allWords.isEmpty()) {
                System.err.println("Size: " + allWords.size() + " za kljucnu rec " + topWords.get(i));
                
            } else {
                //Dodajem u hash tabelu
                mMap.put(topWords.get(i), allWords);
            }
        }

        // Interator za ispisivanje hash tabele
        Iterator iter = mMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
        }

        return (HashMap) mMap;
    }

    // Metoda za sabiranje broja pojavljivanja top reci u hash mapi
    public long getCountOfWords(HashMap<String, List<String>> hm) {
        Iterator iter = hm.entrySet().iterator();
        long wordWeight = 0;
        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();

            //System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
            //Konverujemo vrednosti iz hash tabele u listu
            ArrayList<String> allWordsFromHm = (ArrayList<String>) mEntry.getValue();

            wordWeight += allWordsFromHm.size();
        }
        return wordWeight;
    }

    // Za komparaciju hash tabela
    public int compareTwoHashTables(HashMap<String, List<String>> hm1, HashMap<String, List<String>> hm2, int overlaps) {
        // Ispitujemo da li je nadjeno reci koliko je zadato u Config.java fajlu,
        // ako nije, onda predefinisemo na ukupan broj nadjenih najkoriscenijih reci
        int saveValue = Config.numberFrequentWords;
        if (Config.numberFrequentWords >= overlaps) {
            Config.numberFrequentWords = overlaps;
        }

        // Interator za ispisivanje hash tabele
        Iterator iter = hm1.entrySet().iterator();

        double percentageOfMatches = 0;

        double sumOfWordWeight = getCountOfWords(hm1) + getCountOfWords(hm2);

        int j = 1;
        while (iter.hasNext()) {

            Map.Entry mEntry = (Map.Entry) iter.next();
            if (hm2.containsKey(mEntry.getKey())) {

                //Konverujemo vrednosti iz hash tabele u listu
                ArrayList<String> allWordsFromHm1 = (ArrayList<String>) mEntry.getValue();
                ArrayList<String> allWordsFromHm2 = (ArrayList<String>) hm2.get(mEntry.getKey());

                ArrayList<String> common = this.compareTwoLists(allWordsFromHm1, allWordsFromHm2);

                // Ako je zajednickih elemenata ima 0, doslo je do greske pri parsiranju
                if (allWordsFromHm1.isEmpty() || allWordsFromHm1.isEmpty()) {
                    break;
                }

                double numberOfSetsInRow = allWordsFromHm1.size() + allWordsFromHm2.size();
                double wordWeight = numberOfSetsInRow / sumOfWordWeight;

                // Broja istih reci u jednom redu hashmape
                System.out.println("------------ Red " + j + " ------------" + " Procenat wordWeight: " + wordWeight +" = " + numberOfSetsInRow + " / " + sumOfWordWeight);
                for (int i = 0; i < common.size(); i++) {
                   // System.out.println("Isti delovi rečenice: " + common.get(i));
                }

                // Razliciti elemenetni u lista se gledaju da bi se dobio ispravan procenat poklapanja
                double different = this.getNumberOfDifferentElements(allWordsFromHm1, allWordsFromHm2);
                double commonSize = common.size();

                // Procenat poklapanja u jednom redu u dve hashmape
                double matchesInRow = commonSize / different;

                percentageOfMatches += matchesInRow * wordWeight * 100;
                System.out.println("------------ Red " + j + " ------------" + "\tProcenat poklapanja: " + percentageOfMatches);
                System.out.println(matchesInRow + " = " + commonSize + " / " + different);
                j++;
            }
        }
        System.out.println("***************" + percentageOfMatches + "***************");
        percentageOfMatches = percentageOfMatches * overlaps / (Config.numberFrequentWords+1);

        //Vracamo broj na default vrednost
        Config.numberFrequentWords = saveValue;
        return (int) percentageOfMatches;
    }

    // Metoda za komparaciju lista
    public ArrayList<String> compareTwoLists(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++) {
            if (!res.contains(list1.get(i))) { //If resultant list doesn't contain element
                for (int j = 0; j < list2.size(); j++) {
                    if (list1.get(i).equals(list2.get(j))) {
                        res.add(list1.get(i));
                        break;
                    }
                }
            }
        }
        return res;
    }

    // Metoda za komparaciju lista
    public int getNumberOfDifferentElements(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++) {
            if (!res.contains(list1.get(i))) { 
                res.add(list1.get(i));
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            if (!res.contains(list2.get(i))) { 
                res.add(list2.get(i));
            }
        }

        return res.size();
    }

}
