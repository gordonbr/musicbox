package org.jonathas.musicbox.service;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;

import java.util.List;

public interface JukeBoxService {

    public List<JukeBox> getJukeBoxList();
    public List<JukeBoxSetting> getSettingsList();
}
