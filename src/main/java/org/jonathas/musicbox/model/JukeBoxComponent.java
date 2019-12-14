package org.jonathas.musicbox.model;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "All details about the components of a jukebox.")
public class JukeBoxComponent {

    private String name;

    public JukeBoxComponent(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
