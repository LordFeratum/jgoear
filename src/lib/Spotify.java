package lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spotify.SpotifyTableModel;

public class Spotify extends Thread {

    private ArrayList<Song> songs;
    private ArrayList<String> urls;
    private SpotifyTableModel stb;

    public Spotify(String[] urls, SpotifyTableModel stb) {
        this.urls = new ArrayList();
        this.urls.addAll(Arrays.asList(urls));
        this.songs = new ArrayList();
        this.stb = stb;
    }

    public void search() throws IOException, IllegalArgumentException {
        Iterator<String> it = urls.iterator();
        String loe = "LIST OF ELEMENTS TAG AT GOEAR.COM"
        while (it.hasNext()) {
            Document spotifyUrl = Jsoup.connect(it.next()).get();
            Elements info = spotifyUrl.select("div.player-header");
            String songName = info.select("h1").first().text();
            String songArtist = info.select("h2").select("a").first().text();
            String songAlbum = spotifyUrl.select("h3").select("a").first().text();
            String queryStr = songName + " " + songArtist;
            String q = queryStr.replaceAll("[^a-zA-Z0-9 ]", "");
            String q1 = Utils.normalize(q.replaceAll("[ ]+", "+"));
            try {
                Document searchPage = Jsoup.connect(Goear.GOEAR_SEARCH + "/" + q1).get();
                Element e = searchPage.select(loe).first();
                String songID = e.getElementsByTag("a").attr("href").split("/")[1];
                Document xmlDocument = Jsoup.connect(Goear.GOEAR_XML + "?f=" + songID).get();
                String songURL = xmlDocument.select("songs").select("song").attr("path");
                Song song = new Song(songName, songArtist, songAlbum, songURL);
                songs.add(song);
                stb.addSong(song);
            } catch (ArrayIndexOutOfBoundsException e) {
                try {
                    queryStr = songName;
                    q = queryStr.replaceAll("[^a-zA-Z0-9 ]", "");
                    q1 = Utils.normalize(q.replaceAll("[ ]+", "+"));
                    Document searchPage1 = Jsoup.connect(Goear.GOEAR_SEARCH + "/" + q1).get();
                    Element e1 = searchPage1.select("ol#results").first();
                    String songID = e1.getElementsByTag("a").attr("href").split("/")[1];
                    Document xmlDocument1 = Jsoup.connect(Goear.GOEAR_XML + "?f=" + songID).get();
                    String songURL = xmlDocument1.select("songs").select("song").attr("path");
                    Song song = new Song(songName, songArtist, songAlbum, songURL);
                    songs.add(song);
                    stb.addSong(song);
                } catch (ArrayIndexOutOfBoundsException e2) {
                }
            }

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
        try {
            this.search();
        } catch (IOException ex) {
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,
                    "The clipboard's content is not a Spotify or a URL", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }
}
