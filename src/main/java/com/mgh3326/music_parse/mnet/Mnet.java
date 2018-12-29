package com.mgh3326.music_parse.mnet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mgh3326.music_parse.Music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Mnet {
    private Music music;

    public Mnet() {
        this.music = new Music();
    }

    public HttpURLConnection url_to_con(String urlToRead) throws IOException {
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";

        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", userAgent);
        return conn;
    }

    public String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
//
//        conn.setRequestMethod("GET");
//        //conn.setRequestProperty("Content-Type", "application/json");
//        conn.setRequestProperty("User-Agent", userAgent);
        HttpURLConnection conn = url_to_con(urlToRead);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    String TestImagePath(String path) throws IOException {
//        URL url = new URL(path);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
//        conn.setRequestMethod("GET");
//        //conn.setRequestProperty("Content-Type", "application/json");
//        conn.setRequestProperty("User-Agent", userAgent);
        HttpURLConnection conn = url_to_con(path);

        if (conn.getResponseCode() == 200) {
            return path;

        } else {
            if (path.charAt(39) == '2') {
                return "";
            }
            path = path.replaceFirst("8", "4");
            path = path.replaceFirst("4", "2");
            TestImagePath(path);

        }
        return "";
    }

    String SearchImagePath(String album_id) throws IOException {
        String mImagePath = "";

        if (album_id.length() == 7) {
            mImagePath =
                    "http://cmsimg.mnet.com/clipimage/album/480/00" + album_id.charAt(0) + "/" +
                            album_id.substring(1, 4) + "/" + album_id + ".jpg";
        } else if (album_id.length() == 4) {
            mImagePath =
                    "http://cmsimg.mnet.com/clipimage/album/480/00" + 0 + "/" +
                            "00" + album_id.charAt(0) + "/" + album_id + ".jpg";
        } else if (album_id.length() == 5) {
            mImagePath =
                    "http://cmsimg.mnet.com/clipimage/album/480/00" + 0 + "/" +
                            "0" + album_id.substring(0, 2) + "/" + album_id + ".jpg";
        } else {
            mImagePath =
                    "http://cmsimg.mnet.com/clipimage/album/480/00" + 0 + "/" +
                            album_id.substring(0, 3) + "/" + album_id + ".jpg";
        }
        mImagePath = TestImagePath(mImagePath);
        return mImagePath;

    }

    public Music search(String search_name) throws Exception {
        String urlString = "http://search.api.mnet.com/search/totalweb?q=" + URLEncoder.encode(search_name, "UTF-8") + "&sort=r";
        String json = getHTML(urlString);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        String resultCode = element.getAsJsonObject().get("resultCode").getAsString();
        if (resultCode.equals("S0000")) {
            int songcnt = element.getAsJsonObject().get("info").getAsJsonObject().get("songcnt").getAsInt();
            if (songcnt != 0) {
                this.music.setmTitle(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("songnm").getAsString());
                this.music.setmArtist(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("ARTIST_NMS").getAsString());
                this.music.setmAlbum(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("albumnm").getAsString());
                this.music.setmReleased(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("releaseymd").getAsString());
                this.music.setmGenre(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("genrenm").getAsString());
                String album_id = element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("albumid").getAsString();
                this.music.setmImagePath(this.SearchImagePath(album_id));

                if (element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("songnm").getAsString().length() != 0) {
                    this.music.setmLyric(element.getAsJsonObject().get("data").getAsJsonObject().get("songlist").getAsJsonArray().get(0).getAsJsonObject().get("songnm").getAsString());

                }

            } else {
                int mvcnt = element.getAsJsonObject().get("info").getAsJsonObject().get("tvcnt").getAsInt();
                if (mvcnt != 0) {
                    String mvtitle = element.getAsJsonObject().get("data").getAsJsonObject().get("tvlist").getAsJsonArray().get(0).getAsJsonObject().get("mvtitle").getAsString();
                    this.search(mvtitle);
                } else {
                    this.music.setmResultCode(1);

                }

            }
        }
        return this.music;
    }
}
