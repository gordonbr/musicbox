package org.jonathas.musicbox.service;

import com.google.gson.Gson;
import org.jonathas.musicbox.mock.MockedRestTemplate;
import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * A mocked implementation of JukeboxService that reads the data form a json file
 * @author jonathas
 */
@Component("jukeBoxServiceMock")
@Profile("dev")
public class JukeBoxServiceMocked implements JukeBoxService{

    @Autowired
    private Environment env;

    @Autowired
    private MockedRestTemplate mockedRestTemplate;

    private Logger logger = LoggerFactory.getLogger(JukeBoxServiceMocked.class);
    private Gson gson = new Gson();

    public List<JukeBox> getJukeBoxList() {
        logger.debug("Called getJukeBoxList mocked");
        String uri = env.getProperty("listJukeBoxesUri");
        String jsonResponse = mockedRestTemplate.getForEntity(uri);
        JukeBox[] jukeBoxes = gson.fromJson(jsonResponse, JukeBox[].class);
        return Arrays.asList(jukeBoxes);
    }

    public List<JukeBoxSetting> getSettingsList() {
        logger.debug("Called getJukeBoxList mocked");
        String uri = env.getProperty("listJukeBoxSettingsUri");
        String jsonResponse = mockedRestTemplate.getForEntity(uri);
        JukeBoxSetting[] jukeBoxSettings = gson.fromJson(jsonResponse, JukeBoxSetting[].class);
        return Arrays.asList(jukeBoxSettings);
    }
}
