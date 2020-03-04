import java.util.Timer;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.InputMismatchException;
/**
 * Elijah Helmandollar
 * Lab #3
 * Description: Accepts the URL and runtime from the user to then begin the process of "crawling" the web.
 */
public class Interface {

    // Runs the user interface
    public static void main (String [] args) {

        Scanner scan = new Scanner(System.in);
        Crawler crawler = new Crawler();

        try {

            System.out.print("Please enter a starting URL: ");
            String url = scan.next();

            // Validate the URL in RegEx
            if (Filter.urlFilter(url)) {

                // Get the runtime (in seconds)
                System.out.print("How long (in seconds) would you like to crawl? ");
                long runtime = scan.nextLong();

                // Create a timer on a separate thread
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {

                    public void run() {

                        timer.cancel();

                        System.out.println();
                        System.out.println("All done! Generating report...");
                        System.out.println();

                        // Send the filtered extracted data to the 'Reporter'
                        Reporter.generateReport(Filter.filter(crawler.contents));

                        // Celebrate a successful report
                        printASCII();
                        System.out.println();

                        System.out.println();
                        System.out.println("Please navigate to 'output.txt' for results.");
                        System.out.println();

                        System.exit(0);

                    }

                };

                timer.schedule(task, runtime * 1000);

                System.out.println();
                System.out.println("Crawling...");
                System.out.println();

                // Send the URL to the 'Crawler'
                crawler.crawl(url);

            } else {
                System.out.println("That is not a valid URL");
            }

        } catch (InputMismatchException ex) {
            System.out.println("Invalid input");
        }

    }

    public static void printASCII () {

        System.out.println("    ____                        __                                      __           ____");
        System.out.println("   / __ \\___  ____  ____  _____/ /_   ____ ____  ____  ___  _________ _/ /____  ____/ / /");
        System.out.println("  / /_/ / _ \\/ __ \\/ __ \\/ ___/ __/  / __ `/ _ \\/ __ \\/ _ \\/ ___/ __ `/ __/ _ \\/ __  / / ");
        System.out.println(" / _, _/  __/ /_/ / /_/ / /  / /_   / /_/ /  __/ / / /  __/ /  / /_/ / /_/  __/ /_/ /_/  ");
        System.out.println("/_/ |_|\\___/ .___/\\____/_/   \\__/   \\__, /\\___/_/ /_/\\___/_/   \\__,_/\\__/\\___/\\__,_(_)   ");
        System.out.println("          /_/                      /____/                                                ");

    }

}
