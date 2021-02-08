package dk.dan.itunes.controller;

import dk.dan.itunes.dao.MusicDAO;
import dk.dan.itunes.model.Artist;
import dk.dan.itunes.model.Genre;
import dk.dan.itunes.model.Track;
import dk.dan.itunes.model.TrackLite;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class ViewController {
    MusicDAO musicDAO = new MusicDAO();

    @GetMapping("/")
    public String home(Model model) {
        List<Artist> randomArtists = musicDAO.getFiveRandomArtists();
        List<TrackLite> randomTracks = musicDAO.getFiveRandomTracks();
        List<Genre> randomGenres = musicDAO.getFiveRandomGenres();
        model.addAttribute("artists", randomArtists);
        model.addAttribute("tracks", randomTracks);
        model.addAttribute("genres", randomGenres);
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model) {
        List<Track> tracks = musicDAO.getSearchResult(search);
        model.addAttribute("tracks", tracks);
        model.addAttribute("query", search);
        return "searchResults";
    }

}
