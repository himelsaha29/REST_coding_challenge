package com.assignment.Assignment.challenge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TunesController {


    @GetMapping("/compatiblejukes/{settingsId}")
    public ArrayList<String> getJukebox(@PathVariable("settingsId") String settingsId) {
        return Tunes.get(settingsId, "", "", "");
    }


}
