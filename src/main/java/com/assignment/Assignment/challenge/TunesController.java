package com.assignment.Assignment.challenge;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TunesController {


    @RequestMapping(value = "/compatiblejukes/{settingsId}")
    public List<String> getJukebox(@PathVariable(name = "settingsId", required = true) String settingsId,
                                   @RequestParam(name = "model", defaultValue = "") String model,
                                   @RequestParam(name = "offset", defaultValue = "-1") String offset,
                                   @RequestParam(name = "limit", defaultValue = "-1") String limit) {
        return Tunes.get(settingsId, model, offset, limit);
    }


}
