/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.pd;

/**
 *
 * @author Predrag
 */
public class Word implements Comparable<Word> {

    private String word;
    private int count;
    
    public Word(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }
    
    public void setWord(String word) {
        this.word = word;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public void incrementCount() {
        count++;
    }
    @Override
    public int hashCode() {
        return word.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return word.equals(((Word) obj).word);
    }

    @Override
    public int compareTo(Word b) {
        return b.count - count;
    }
}
