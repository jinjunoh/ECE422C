package assignment4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Bonus {
    public static void main(String[] args) throws IOException {
        // web scrape a UT Professor's webpage to generate a poem using their biography
        String url = "https://www.ece.utexas.edu/people/faculty/edison-thomaz";
        try{
            Document doc = Jsoup.connect(url).get(); // access the document to webscrape
            Element l = doc.select("p").get(1);
            File t = File.createTempFile("webScrapedContent", ".txt");
            try (PrintWriter out = new PrintWriter(t)) {
                out.println(l.text());
            }
            final GraphPoet nimoy = new GraphPoet(t);
            t.delete();
            System.out.println(nimoy.poem(new File("bonus_input.txt")));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
