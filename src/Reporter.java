import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
/**
 * Elijah Helmandollar
 * Description: Formulates a report of the top 50 most used words based upon the extracted and filtered data.
 */
public class Reporter {

    // An ArrayList of 'Word' objects that have been extracted from the contents
    public static ArrayList<Word> wordsFromContents = new ArrayList<>();

    // Common "stop words"
    public static String [] stopWords = {"a", "the", "The", "in", "on", "for", "from", "with", "and", "or", "you", "I",
            "your", "my", "|", "of", "to", "at", "by", "&nbsp;", "&amp;", "our", "is", "any", "Us", "us", "more", "/",
            "be", "that", "will", "it", "This", "this", "We", "we", "as", "are", "these", "if", "In", "And", "But",
            "but", "Or", "an", "It's", "them", "have", "so"};

    // Formulates a report and writes it to the 'output.txt' file
    public static void generateReport(String filteredContents) {

        // Get the words from the filtered contents of the web page
        extractWords(filteredContents);

        // Filter out the "stop words"
        compareStopWords();

        // Stores the 'word' and 'count' fields as key-value pairs
        HashMap<String, Integer> map = new HashMap<>();

        for (Word word : wordsFromContents) {
            map.put(word.getWord(), word.getCount());
        }

        try {

            // Targets a generic project directory.
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            int limit = 0;

            // Sort map in descending order
            for (String word : sort(map).keySet()) {

                if (limit < 50) {
                    // Write the top 50 ranked words to an output file
                    int value = map.get(word);
                    writer.write(word + " " + value);
                    writer.newLine();
                }

                limit++;

            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("Could not write to file");
        }

    }

    // Extracts individual words from the filtered textual data produced by the 'Filter'
    public static void extractWords(String string) {

        // Splits the contents into individual words and places them into an array
        String [] contents = string.split(" ");

        // For any string in the array of contents
        for (String s : contents) {

            // Used to compare words
            boolean foundWord = false;

            // For any word in the array of extracted words
            for (Word word : wordsFromContents) {

                // If the indexed word equals the word found in the contents
                if (word.getWord().equals(s)) {
                    word.increment();
                    foundWord = true;
                }

            }


            // If the 'word' has not been already found
            if (!foundWord) {

                // If the indexed string is not blank
                if (!s.isBlank()) {

                    // Create a new Word and add it to the ArrayList
                    wordsFromContents.add(new Word(s));

                }

            }

        }

    }

    // Compares the extracted Word to our Array of "stop words"
    public static void compareStopWords () {

        // Using 'Iterator' prevents concurrent modification
        for (Iterator<Word> iterator = wordsFromContents.iterator(); iterator.hasNext();) {

            Word word = iterator.next();

            for (String stopWord : stopWords) {

                if (word.getWord().equals(stopWord)) {
                    iterator.remove();
                }

            }

        }

    }

    // Sort a map in descending order
    public static HashMap<String, Integer> sort(HashMap<String, Integer> map) {

        // Create a new list with the data from 'map'
        List<Map.Entry<String, Integer> > list
                = new LinkedList<>(map.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Put the newly sorted data into a hash map
        HashMap<String, Integer> sortedList = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> element : list) {
            sortedList.put(element.getKey(), element.getValue());
        }

        return sortedList;

    }

}
