package org.jonathas.musicbox.model;

import io.swagger.annotations.ApiModel;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "All details about the JukeBox.")
public class JukeBox {
    private String id;
    private String model;
    private List<JukeBoxComponent> components;

    public JukeBox(String id, String model, List<JukeBoxComponent> componentsList) {
        this.id = id;
        this.model = model;
        if(componentsList != null && !componentsList.isEmpty()) {
            this.components = Collections.unmodifiableList(componentsList);
        } else {
            throw new IllegalArgumentException("ComponentsList can't be neither null nor empty.");
        }
    }

    public String getId() { return id; }

    public String getModel() { return model; }

    public List<JukeBoxComponent> getComponents() { return components; }
}
