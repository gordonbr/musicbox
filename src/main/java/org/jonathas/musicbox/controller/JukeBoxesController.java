package org.jonathas.musicbox.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@Api(value="JukeBoxes Management System")
public class JukeBoxesController {

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = "/jukeboxes")
    @ApiOperation(value = "Find all jukeboxes", response = String.class)
    public String getAllGreetings() {
        return "works";

    }
}
