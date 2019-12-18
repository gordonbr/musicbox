package org.jonathas.musicbox.controller;

import io.swagger.annotations.*;
import org.jonathas.musicbox.business.JukeBoxBusiness;
import org.jonathas.musicbox.model.JukeBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(JukeBoxController.class);

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "SettingId is required in path"),
            @ApiResponse(code = 404, message = "Setting not found")
    })
    public List<JukeBox> getJukeBoxesBySettings(@ApiParam(value = "Jukebox setting id", required = true) @RequestParam(name = "settingId") String settingId,
                                                @ApiParam(value = "Filter result by jukebox model") @RequestParam(name = "model", required = false) String model,
                                                @ApiParam(value = "pagination offset") @RequestParam(name = "offset", required = false) Integer offset,
                                                @ApiParam(value = "pagination limit") @RequestParam(name = "limit", required = false) Integer limit) {
        logger.info(String.format("Called getJukeBoxesBySettings with settingId:%s, model:%s, offset:%d and limit:%d",
                settingId, model, offset, limit));
        return jukeBoxBusiness.getJukeBoxesBySettings(settingId, model, offset, limit);
    }
}
