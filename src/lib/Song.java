package lib;

import java.util.Observable;

public class Song extends Observable {

    private String songID;
    private String songName;
    private String songArtist;
    private String songAlbum;
    private String songBritate;
    private String songURL;
    private String songLength;
    private String songFileName;

    public Song(String songID, String songName, String songArtist, String songBritate, String songLength, String songURL) {
        this.songID = songID;
        this.songName = Utils.capitalize(songName);
        this.songArtist = Utils.capitalize(songArtist);
        this.songAlbum = "";
        this.songBritate = songBritate;
        this.songLength = songLength;
        this.songURL = songURL;

    }

    public Song(String songName, String songArtist, String songAlbum,String songURL) {
        this.songName = Utils.capitalize(songName);
        this.songArtist = Utils.capitalize(songArtist);
        this.songAlbum = Utils.capitalize(songAlbum);
        this.songURL = songURL;

    }

    public String getSongID() {
        return songID;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public String getSongBritate() {
        return songBritate;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getSongURL() {
        return songURL;
    }

    public String generateFileName() {
        String[] songSplitted = this.songURL.split("/");
        String fileExt = Utils.getFileExtension(songSplitted[songSplitted.length - 1]);
        return this.songName + " - " + this.songArtist + "." + fileExt;
    }

    @Override
    public String toString() {
        return this.getSongName() + " - " + this.getSongArtist();
    }
}
