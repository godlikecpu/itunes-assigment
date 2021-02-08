package dk.dan.itunes.model;

/**
 * Represents the track entity
 */
public class Track {
    public String id;
    public String name;
    public String artist;
    public String album;
    public String genre;

    public Track(String id, String name, String artist, String album, String genre) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

}
