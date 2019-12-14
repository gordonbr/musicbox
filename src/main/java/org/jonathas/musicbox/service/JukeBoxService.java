package org.jonathas.musicbox.service;

import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jonathas.musicbox.mock.MockedRestTemplate;
import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class JukeBoxService {

    @Autowired
    private Environment env;

    @Autowired
    private MockedRestTemplate mockedRestTemplate;

    private Gson gson = new Gson();

    public List<JukeBox> getJukeBoxList() {
        String uri = env.getProperty("listJukeBoxesUri");
        String jsonResponse = mockedRestTemplate.getForEntity(uri);
        JukeBox[] jukeBoxes = gson.fromJson(jsonResponse, JukeBox[].class);
        return Arrays.asList(jukeBoxes);
    }

    public List<JukeBoxSetting> getSettingsList() {
        String uri = env.getProperty("listJukeBoxSettingsUri");
        String jsonResponse = mockedRestTemplate.getForEntity(uri);
        JukeBoxSetting[] jukeBoxSettings = gson.fromJson(jsonResponse, JukeBoxSetting[].class);
        return Arrays.asList(jukeBoxSettings);
    }
}
