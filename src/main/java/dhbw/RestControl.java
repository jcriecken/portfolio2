/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw;

import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
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

    @RequestMapping("/search")
    public String handleSearch(@RequestParam("type") RequestCategory type, @RequestParam("query") String title) {
        SpotifyRequest spotifyRequester = new SpotifyRequest(RequestType.SEARCH);
        try {
            json = spotifyRequester.performeRequestSearch(type, title).get();
            System.out.println(json);
        } catch (WrongRequestTypeException wTE) {
            System.out.println(wTE);
        }
        return json;
    }

}
