import com.mgh3326.app.Alsong;
import com.mgh3326.app.Mnet;
import com.mgh3326.app.Music;

import java.net.HttpURLConnection;
import java.net.URL;


public class Main {


    public static void main(String[] args) throws Exception {


// Do what you want with that stream
        Mnet mnet = new Mnet();//객체 호출
        Music music;
        music = mnet.search("아기상어");
        if (music.getmResultCode() == 0) {
            System.out.println(music.getmTitle());
            System.out.println(music.getmImagePath());

            Alsong alsong = new Alsong();//객체 호출
        } else {
            System.out.println("검색 결과가 없습니다.");
        }

    }
}