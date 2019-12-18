package org.jonathas.musicbox.service;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;

import java.util.List;

/**
 * Jukebox service
 * @author jonathas
 */
public interface JukeBoxService {

    /**
     * Searches for all the jukeboxes available
     * @return A List of Jukebox
     */
    public List<JukeBox> getJukeBoxList();

    /**
     * Searches for all the JukeBoxSettings available
     * @return A List of JukeboxSetting
     */
    public List<JukeBoxSetting> getSettingsList();
}
