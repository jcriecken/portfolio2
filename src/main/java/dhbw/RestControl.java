/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw;

import com.fasterxml.jackson.databind.ObjectMapper;
import dhbw.pojo.result.search.SearchResult;
import dhbw.pojo.result.search.SearchResultList;
import dhbw.pojo.search.album.SearchAlbum;
import dhbw.pojo.search.artist.SearchArtist;
import dhbw.pojo.search.track.SearchTrack;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author D067849
 */
@RestController
public class RestControl {

    @RequestMapping("/search")
    public String handleSearch(@RequestParam(value = "type") String type, @RequestParam("query") String query) {
        String json = "";
        String help;
        SpotifyRequest spotifyRequester = new SpotifyRequest(RequestType.SEARCH);
        RequestCategory category = RequestCategory.valueOf(type);
        try {
            help = spotifyRequester.performeRequestSearch(category, query, 10, "DE").get();
            ObjectMapper map = new ObjectMapper();
            List<dhbw.pojo.search.track.Item> tracks;
            List<dhbw.pojo.search.album.Item> albums;
            List<dhbw.pojo.search.artist.Item> artists;
            List<dhbw.pojo.result.search.SearchResultList> results = new ArrayList<>();
            if (type.equals("TRACK")){
                SearchTrack searchTrack = map.readValue(help, SearchTrack.class);
                tracks = searchTrack.getTracks().getItems();
                tracks.forEach((temp) -> {
                    String id = temp.getId();
                    String name = temp.getName();
                    String description = temp.getType();
                    String playLink = temp.getUri();
                    results.add(new SearchResultList(id, name, description, playLink));
                });
            }
            if (type.equals("ALBUM")){
                SearchAlbum searchAlbum = map.readValue(help, SearchAlbum.class);
                albums = searchAlbum.getAlbums().getItems();
                albums.forEach((temp) -> {
                    String id = temp.getId();
                    String name = temp.getName();
                    String description = temp.getType();
                    String playLink = temp.getUri();
                    results.add(new SearchResultList(id, name, description, playLink));
                });
            }
            if (type.equals("ARTIST")){
                SearchArtist searchArtist = map.readValue(help, SearchArtist.class);
                artists = searchArtist.getArtists().getItems();
                artists.forEach((temp) -> {
                    String id = temp.getId();
                    String name = temp.getName();
                    String description = temp.getType();
                    String playLink = temp.getUri();
                    results.add(new SearchResultList(id, name, description, playLink));
                });
            }
            SearchResult searchResult = new SearchResult(query, type, results);
            json = map.writeValueAsString(searchResult);
            
            //System.out.println(json);
            
        } catch (WrongRequestTypeException | IOException e) {
            System.out.println(e);
        }
        return json;
    }

}
