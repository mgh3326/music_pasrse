package com.mgh3326.music_parse.alsong;

import com.mgh3326.music_parse.Music;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Alsong {
    Music music;
    private String lrcData;

    public boolean SearchAlsongServer(String title, String artist) {
        String parameter = CreateSearchString(title, artist);
        return RequestAlsongServerParameter(parameter);
    }

    private String CreateSearchString(String title, String artist) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope " +
                "xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"ALSongWebServer/Service1Soap\" " +
                "xmlns:ns1=\"ALSongWebServer\" xmlns:ns3=\"ALSongWebServer/Service1Soap12\"><SOAP-ENV:Body><ns1:GetResembleLyric2" +
                "><ns1:stQuery><ns1:strTitle>" + title + "</ns1:strTitle><ns1:strArtistName>" + artist +
                "</ns1:strArtistName><ns1:nCurPage>0</ns1:nCurPage></ns1:stQuery></ns1:GetResembleLyric2></SOAP-ENV:Body></SOAP" +
                "-ENV:Envelope>";


        String PostStr_A1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:ns2=\"ALSongWebServer/Service1Soap\" " +
                "xmlns:ns1=\"ALSongWebServer\" xmlns:ns3=\"ALSongWebServer/Service1Soap12\">" +
                "<SOAP-ENV:Body>" +
                "<ns1:GetSyncLyricBySearch>" +
                "<ns1:title>";

        String PostStr_A2 =
                "</ns1:title>" +
                        "<ns1:artist>";

        String PostStr_A3 =
                "</ns1:artist>" + "<ns1:encData>b6437d6c99ef9324af560b3ea659828e63b1087616d9e458e7b0fa2c93ee87e36d9c4aa0094b02b53b0e3bf008c4bfec89898f8cbf15a2f6cc8000e78d71e7899cfbfaffacf100618bb7d0dfa726b67429637d34cc1325e99b68a3e45a2cdeae9ee357832a3697a651a8ae4c52d4ea34746e1e35c1462a4df889a00c5fbe46f9</ns1:encData>" +
                        "</ns1:GetSyncLyricBySearch>" +
                        "</SOAP-ENV:Body>" +
                        "</SOAP-ENV:Envelope>";
        return xml;
//        return PostStr_A1 + title + PostStr_A2 + artist + PostStr_A3;
    }

    public boolean RequestAlsongServerParameter(String parameter) {
        lrcData = "";

        try {
            URL url = new URL("http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx");
            URLConnection con = url.openConnection();

            System.setProperty("http.agent", "gSOAP/2.7");
            con.setRequestProperty("User-Agent", "gSOAP/2.7");
            con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            con.setRequestProperty("Connection", "close");
            con.setRequestProperty("SOAPAction", "ALSongWebServer/GetLyric5");
            con.setDoOutput(true);        // set POST request
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parameter);
            wr.flush();

            // get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
                lrcData += line;
            }

            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }


    public Alsong(Music music) throws ParserConfigurationException, IOException, SAXException {
        this.music = music;

        //boolean temp = this.SearchAlsongServer(URLEncoder.encode(music.getmTitle(), "UTF-8"), URLEncoder.encode(music.getmArtist(), "UTF-8"));
        boolean temp = this.SearchAlsongServer(music.getmTitle(), music.getmArtist());
        System.out.println(temp);
        music.getmTitle();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(lrcData)));
        NodeList nStatus = document.getElementsByTagName("strStatusID");
        NodeList nTitle = document.getElementsByTagName("strTitle");
        NodeList nArtist = document.getElementsByTagName("strArtist");
        NodeList nAlbum = document.getElementsByTagName("strAlbum");
        NodeList nLyrics = document.getElementsByTagName("strLyric");
        NodeList nCreator = document.getElementsByTagName("strRegisterFirstName");
        NodeList nLRCCreator = document.getElementsByTagName("strRegisterName");
        String Title = nTitle.item(0).getTextContent();
        String test = nLyrics.item(0).getTextContent();
        System.out.println(Title);

    }
}
