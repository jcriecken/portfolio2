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

    private String json;
    private String text;

    @RequestMapping("/search")
    public String handleSearch(@RequestParam(value = "type") String type, @RequestParam("query") String query) {
        SpotifyRequest spotifyRequester = new SpotifyRequest(RequestType.SEARCH);
        RequestCategory category = RequestCategory.valueOf(type);
        try {
            json = spotifyRequester.performeRequestSearch(category, query).get();
            ObjectMapper map = new ObjectMapper();
            List<dhbw.pojo.search.track.Item> tracks = new ArrayList<>();
            List<dhbw.pojo.search.album.Item> albums = new ArrayList<>();
            List<dhbw.pojo.search.artist.Item> artists = new ArrayList<>();
            List<dhbw.pojo.result.search.SearchResultList> results = new ArrayList<>();
            if (type.equals("TRACK")){
                SearchTrack searchT = map.readValue(json, SearchTrack.class);
                tracks = searchT.getTracks().getItems();
                for (dhbw.pojo.search.track.Item temp : tracks){
                    String id = temp.getId();
                    String name = temp.getName();
                    String genre = temp.getType();
                    String uri = temp.getUri();
                    
                }
            }
            if (type.equals("ALBUM")){
                
            }
            if (type.equals("ARTIST")){
                
            }
            
            
            //System.out.println(json);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return json;
    }

}
