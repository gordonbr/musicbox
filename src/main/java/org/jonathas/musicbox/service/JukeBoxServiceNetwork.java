package org.jonathas.musicbox.service;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxSetting;
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
import java.util.List;

@Component("jukeBoxServiceNetwork")
@Profile("prod")
public class JukeBoxServiceNetwork implements JukeBoxService {

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(JukeBoxServiceNetwork.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<JukeBox> getJukeBoxList() {
        logger.debug("Called getJukeBoxList network");
        String uri = env.getProperty("listJukeBoxesUri");
        return new ArrayList<>();
    }

    @Override
    public List<JukeBoxSetting> getSettingsList() {
        logger.debug("Called getJukeBoxList network");
        String uri = env.getProperty("listJukeBoxesUri");
        uri = "http://localhost:7052/zuora/signature";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        String value = responseEntity.getBody();
        HttpStatus status = responseEntity.getStatusCode();
        return new ArrayList<>();
    }
}
