package org.jonathas.musicbox.mock;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class that mocks the behaviour of Spring RestTemplate class
 */
@Component
public class MockedRestTemplate {

    private static final String JUKEBOX_URI = "http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes";
    private static final String JUKEBOX_SETTINGS_URI = "http://my-json-server.typicode.com/touchtunes/tech-assignment/settings";
    private JSONParser jsonParser = new JSONParser();

    public String getForEntity(String uri) {
        if (uri != null && uri.equals(JUKEBOX_URI)) {
            return getJukeBoxes();
        } else if (uri != null && uri.equals((JUKEBOX_SETTINGS_URI))) {
            return getJukeBoxesSettings();
        } else {
            return null;
        }
    }

    private String getJukeBoxes() {
        JSONArray jsonArray = (JSONArray) readFile("mock/mockJukeBox.json");
        return jsonArray.toString();
    }

    private String getJukeBoxesSettings() {
        JSONObject jsonObject = (JSONObject) readFile("mock/mockJukeBoxSettings.json");
        return jsonObject.get("settings").toString();
    }

    private Object readFile(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String content = new String(bdata, StandardCharsets.UTF_8);
            return jsonParser.parse(content);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }
}
