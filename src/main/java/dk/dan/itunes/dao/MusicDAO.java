package dk.dan.itunes.dao;

import dk.dan.itunes.model.Artist;
import dk.dan.itunes.model.Genre;
import dk.dan.itunes.model.Track;
import dk.dan.itunes.model.TrackLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public List<Artist> getFiveRandomArtists() {
        List<Artist> artists = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT ArtistId, Name from Artist ORDER BY RANDOM() LIMIT 5");
            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Results
            while (resultSet.next()) {
                artists.add(new Artist(
                        resultSet.getString("ArtistId"),
                        resultSet.getString("Name")
                ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return artists;
        }
    }

    public List<Genre> getFiveRandomGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT genreId, Name from Genre ORDER BY RANDOM() LIMIT 5");
            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Results
            while (resultSet.next()) {
                genres.add(new Genre(
                        resultSet.getString("GenreId"),
                        resultSet.getString("Name")
                ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return genres;
        }
    }

    public List<TrackLite> getFiveRandomTracks() {
        List<TrackLite> tracks = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT TrackId, Name from Track ORDER BY RANDOM() LIMIT 5");
            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Results
            while (resultSet.next()) {
                tracks.add(new TrackLite(
                        resultSet.getString("TrackId"),
                        resultSet.getString("Name")
                ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return tracks;
        }
    }

    public List<Track> getSearchResult(String searchString) {
        List<Track> tracks = new ArrayList<>();
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT TrackId, Track.Name AS TrackName, A2.Name AS ArtistName, G.Name AS GenreName, Title AS AlbumTitle FROM Track INNER JOIN Album A on A.AlbumId = Track.AlbumId INNER JOIN Artist A2 on A2.ArtistId = A.ArtistId INNER JOIN Genre G on Track.GenreId = G.GenreId WHERE Track.Name LIKE ?");
            preparedStatement.setString(1, "%" + searchString + "%");
            // Execute Statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process Results
            while (resultSet.next()) {
                tracks.add(new Track(
                        resultSet.getString("TrackId"),
                        resultSet.getString("TrackName"),
                        resultSet.getString("ArtistName"),
                        resultSet.getString("AlbumTitle"),
                        resultSet.getString("GenreName")
                ));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong...");
            e.printStackTrace();
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception e) {
                System.out.println("Something went wrong while closing connection.");
                e.printStackTrace();
            }
            return tracks;
        }
    }


}
