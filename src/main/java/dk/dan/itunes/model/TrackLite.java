package dk.dan.itunes.model;


/**
 * Represents a smaller version of the track entity
 */
public class TrackLite {
    public String id;
    public String name;

    public TrackLite(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
