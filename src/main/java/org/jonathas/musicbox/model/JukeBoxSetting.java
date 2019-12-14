package org.jonathas.musicbox.model;

import io.swagger.annotations.ApiModel;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "All details about the settings for a jukebox.")
public class JukeBoxSetting {
    private String id;
    private List<String> requires;

    public JukeBoxSetting(String id, List<String> requiresList) {
        this.id = id;
        if(requiresList != null && !requiresList.isEmpty()) {
            this.requires = Collections.unmodifiableList(requiresList);
        } else {
            throw new IllegalArgumentException("RequiresList can't be neither null nor empty.");
        }
    }

    public String getId() { return id; }

    public List<String> getRequires() { return requires; }
}
