package org.jonathas.musicbox.service;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JukeBoxServiceTest {

    @Autowired
    JukeBoxServiceMocked jukeBoxServiceMocked;

    @Test
    public void getJukeBoxListTest() {
        List<JukeBox> jukeBoxList = jukeBoxServiceMocked.getJukeBoxList();
        Assert.assertEquals(jukeBoxList.size(), 4);
    }

    @Test
    public void getSettingsListTest() {
        List<JukeBoxSetting> jukeBoxSettings = jukeBoxServiceMocked.getSettingsList();
        Assert.assertEquals(jukeBoxSettings.size(), 5);
    }
}
