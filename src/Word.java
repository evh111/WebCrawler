/**
 * Elijah Helmandollar
 * Description: A simple class that allows us to save each instance of a word to an
 *              array list of un-duplicated words.
 */
public class Word {

    String word;

    // The number of instances of the same word the crawler has extracted
    int count;

    public Word (String string) {
        word = string;
        count = 1;
    }

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public String getWord() { return word; }

}
