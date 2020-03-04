import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
/**
 * Elijah Helmandollar
 * Lab #3
 * Description: Repeatedly extracts data from an entered URL, and then locates the sub-URLs within that data and
 *              extracts more data/sub-URLs.
 */
public class Crawler {

    public String contents = "";

    // Separate the URLs from the extracted sub URLs to extract their unfiltered contents
    public void crawl(String startingUrl) {

        ArrayList<String> listOfTraveledURLs = new ArrayList<>();

        ArrayList<String> listOfGatheredURLs = new ArrayList<>();

        listOfGatheredURLs.add(startingUrl);

        // 'listOfGatheredURLs' will never be empty
        while (!listOfGatheredURLs.isEmpty()) {

            // Convert the URL to a String and remove it from the list
            String url = listOfGatheredURLs.remove(0);

            // If list of traveled URLs does not contain the 'url'
            if (!listOfTraveledURLs.contains(url)) {

                // Add the 'url' to the 'listOfTraveledURLs'
                listOfTraveledURLs.add(url);
                System.out.println("Crawl URL: " + url);

                // Gather and save the data from the passed-in URL
                getContents(url);

                for (String subURL : getSubURLs(url)) {

                    if (!listOfTraveledURLs.contains(subURL) && Filter.urlFilter(subURL))  {

                        listOfGatheredURLs.add(subURL);
                        System.out.println("Crawl Sub-URL: " + subURL);

                        // Gather the data from the subURLs and save them to the 'filteredContents'
                        getContents(subURL);

                    }

                }

            }

        }

    }

    // Extracts the unfiltered contents of a URL (String) saves it to 'contents'
    public void getContents(String urlString) {

        try {

            URL url = new URL(urlString);
            Scanner scan = new Scanner(url.openStream());

            while (scan.hasNext()) {
                contents += scan.nextLine();
            }

        }

        catch (IOException ex) {
            // No message as this is similar to the one in 'getSubURLs'
            System.out.println();
        }

    }

    // Navigates through the unfiltered contents of the URL and extracts sub URLs
    public ArrayList<String> getSubURLs(String urlString) {

        ArrayList<String> listOfSubURLs = new ArrayList<>();

        try {

            URL url = new URL(urlString);
            Scanner scan = new Scanner(url.openStream());

            int currentLine = 0;

            while (scan.hasNext()) {

                // Finds the beginning of the next URL in the contents
                String line = scan.nextLine();
                currentLine = line.indexOf("http:", currentLine);

                while (currentLine > 0) {

                    int endIndex = line.indexOf("\"", currentLine);

                    if (endIndex > 0) {

                        listOfSubURLs.add(line.substring(currentLine, endIndex));
                        currentLine = line.indexOf("http:", endIndex);

                    } else {
                        // Set to -1 so that the program does not repeat
                        currentLine = -1;
                    }

                }

            }

        }
        catch (IOException ex) {
            System.err.println("Error: Cannot extract data from URL, please try to include 'https://' prefix");

            // Close all threads
            System.exit(0);
        }

        return listOfSubURLs;

    }

}
