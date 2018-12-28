import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Window, Chrome의 User Agent.
        String userAgent = " 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
        Document doc = Jsoup.connect("http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu").userAgent(userAgent).get();
        System.out.println(doc.title());


    }
}