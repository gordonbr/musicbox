package org.jonathas.musicbox.service;

import com.google.gson.Gson;
import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.jonathas.musicbox.model.ResponseJukeBoxSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("jukeBoxServiceNetwork")
@Profile("prod")
public class JukeBoxServiceNetwork implements JukeBoxService {

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(JukeBoxServiceNetwork.class);
    private RestTemplate restTemplate = new RestTemplate();
    private Gson gson = new Gson();

    @Override
    public List<JukeBox> getJukeBoxList() {
        logger.debug("Called getJukeBoxList network");
        String uri = env.getProperty("listJukeBoxesUri");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        HttpStatus status = responseEntity.getStatusCode();

        if (status != HttpStatus.OK) {
            String errorMessage = String.format("Error in JukeBoxServiceNetwork when calling getJukeBoxList. STATUS: %s, BODY: %s", responseEntity.getStatusCode(), responseEntity.getBody());
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        String bodyValue = responseEntity.getBody();
        JukeBox[] responseJukeBoxes = gson.fromJson(bodyValue, JukeBox[].class);
        return Arrays.asList(responseJukeBoxes);
    }

    @Override
    public List<JukeBoxSetting> getSettingsList() {
        logger.debug("Called getSettingsList network");
        String uri = env.getProperty("listJukeBoxSettingsUri");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        HttpStatus status = responseEntity.getStatusCode();

        if (status != HttpStatus.OK) {
            String errorMessage = String.format("Error in JukeBoxServiceNetwork when calling getSettingsList. STATUS: %s, BODY: %s", responseEntity.getStatusCode(), responseEntity.getBody());
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        String bodyValue = responseEntity.getBody();
        ResponseJukeBoxSetting responseJukeBoxSetting = gson.fromJson(bodyValue, ResponseJukeBoxSetting.class);
        return Arrays.asList(responseJukeBoxSetting.getSettings());
    }
}
