package org.jonathas.musicbox.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jonathas.musicbox.business.JukeBoxBusiness;
import org.jonathas.musicbox.model.JukeBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="JukeBoxes Management System")
public class JukeBoxController {

    @Autowired
    private JukeBoxBusiness jukeBoxBusiness;

    /**
     * Returns a paginated list of jukeboxes that support a given setting id
     * @param settingId Setting id that the jukeboxes should support
     * @param model filter the result by jukebox model
     * @param offset specifies at what index start the page
     * @param limit specifies the page size
     * @return the list of jukeboxes that match the given parameters
     */
    @GetMapping(value = "/jukeboxes")
    @ApiOperation(value = "Find all jukeboxes to a given setting", response = String.class)
    public List<JukeBox> getJukeBoxesBySettings(@RequestParam(name = "settingId") String settingId,
                                                @RequestParam(name = "model", required = false) String model,
                                                @RequestParam(name = "offset", required = false) Integer offset,
                                                @RequestParam(name = "limit", required = false) Integer limit) {
        return jukeBoxBusiness.getJukeBoxesBySettings(settingId, model, offset, limit);
    }
}
