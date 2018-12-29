import com.mgh3326.music_parse.Music;
import com.mgh3326.music_parse.alsong.Alsong;
import com.mgh3326.music_parse.lyrics.LyricsData;
import com.mgh3326.music_parse.lyrics.LyricsParser;
import com.mgh3326.music_parse.mnet.Mnet;


public class Main {

    public static void test(String title, String artist) {

        /* work start */
        if (title.length() > 0) {
            LyricsParser lp = new LyricsParser();
            lp.SearchAlsongServer(title, artist);
            LyricsData arrLyrics = new LyricsData();
            if (lp.parseLyricsResult(arrLyrics)) {
//성공 했을때
                System.out.println("성공");
                for (int i = 0; i < arrLyrics.arrLyrics.size(); i++) {
                    System.out.print(arrLyrics.arrLyrics.get(i).time + " : ");
                    System.out.println(arrLyrics.arrLyrics.get(i).lyrics);
                }
                System.out.println();
            } else {
                System.out.println("실패");
                //실패
            }
        }
        /* work end */

    }

    public static void main(String[] args) throws Exception {


// Do what you want with that stream
        Mnet mnet = new Mnet();//객체 호출
        Music music;
        music = mnet.search("러블리즈");
        if (music.getmResultCode() == 0) {
            System.out.println(music.getmTitle());
            System.out.println(music.getmImagePath());
            test(music.getmTitle(), music.getmArtist());
        } else {
            System.out.println("검색 결과가 없습니다.");
        }
        Alsong alsong = new Alsong(music);//객체 호출

    }
}