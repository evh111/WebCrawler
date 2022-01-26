import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
/**
 * Elijah Helmandollar
 * Description: Filters and extracts only the relevant textual data from the contents of the web page. Also contains
 *              a method for URL validation.
 */
public class Filter {

    // Filters through Jsoup and returns the relevant textual data
    public static String filter (String contents) {
        Document doc = Jsoup.parse(contents);
        return Jsoup.clean(doc.toString(), Whitelist.none());
    }

    // RegEx filter for URLs
    public static boolean urlFilter(String url) {
        return url.matches("^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$");
    }

}
