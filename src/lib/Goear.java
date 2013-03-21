package lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.SearchTableModel;
import settings.SettingsFile;

public class Goear extends Thread {

    public static final String GOEAR_SEARCH = "http://www.goear.com/search";
    public static final String GOEAR_XML = "http://www.goear.com/tracker758.php";
    private ArrayList<Song> songs;
    private String query;
    private SearchTableModel tableModel;
    
    public Goear(String query){
        this.query = query;
        this.songs = new ArrayList();
    }

    public Goear(String query, SearchTableModel stb) {
        songs = new ArrayList();
        this.query = query;
        this.tableModel = stb;
    }


    public void search() {
        try {
            String loe = Jsoup.connect(GOEAR_TAGS+"listOfElements").get().select("body").html();
            String st = "SONG TITLE TAG IN GOEAR.COM";
            String sa =  "SONG ARTIST TAG IN GOEAR.COM";
            Document searchPage = Jsoup.connect(GOEAR_SEARCH + "/" + query.replaceAll(" ", "+")).get();
            int n = 0;
            int page = 1;
            int j = 0;
            boolean exit = false;
            int items = Integer.parseInt(new SettingsFile().getItems());
            while (!exit) {
                Elements listOfElements;
                if (j == 10) {
                    j = 0;
                    page++;
                    searchPage = Jsoup.connect(GOEAR_SEARCH + "/" + query.replaceAll(" ", "+") + "/" + page).get();
                }
                listOfElements = searchPage.select(loe).select("li");
                for (Iterator<Element> it = listOfElements.iterator(); it.hasNext();) {
                    Element e = it.next();
                    String songID = e.getElementsByTag("a").attr("href").split("/")[1];
                    String songName = e.getElementsByTag("span").select(st).text();
                    String songArtist = e.getElementsByTag("span").select(sa).text();
                    String songBritate = e.getElementsByTag("p").html().split(" | ")[0] + " kbps";
                    String songLength = e.getElementsByTag("span").last().html();
                    Document xmlDocument = Jsoup.connect(GOEAR_XML + "?f=" + songID).get();
                    String songURL = xmlDocument.select("songs").select("song").attr("path");
                    Song song = new Song(songID, songName, songArtist, songBritate, songLength, songURL);
                    songs.add(n, song);
                    if(tableModel != null) {
                        tableModel.addSong(song);
                    }
                    n++;
                    j++;
                    if (n == items) {
                        exit = true;
                        break;
                    }
                }

            }
        } catch (IOException ex) {
        }
    }

    public void clearSongs() {
        songs.clear();
    }

    public ArrayList getSongs() {
        return this.songs;
    }

    public Song getSong(int idx) {
        return this.songs.get(idx);
    }

    @Override
    public void run() {
        this.search();
    }
}
