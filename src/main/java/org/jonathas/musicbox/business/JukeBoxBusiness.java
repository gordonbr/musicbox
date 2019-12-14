package org.jonathas.musicbox.business;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.jonathas.musicbox.service.JukeBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JukeBoxBusiness {

    @Autowired
    private JukeBoxService jukeBoxService;

    public List<JukeBox> getJukeBoxesBySettings(String settingId, String model, Integer offset, Integer limit) {

        List<JukeBox> jukeBoxList = jukeBoxService.getJukeBoxList();
        List<JukeBoxSetting> jukeBoxSettings = jukeBoxService.getSettingsList();
        return jukeBoxList;
    }
}
