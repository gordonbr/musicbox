package org.jonathas.musicbox.controller;

import org.jonathas.musicbox.business.JukeBoxBusiness;
import org.jonathas.musicbox.exceptions.EntityNotFoundException;
import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxComponent;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JukeBoxControllerTest {

    @MockBean
    private JukeBoxBusiness jukeBoxBusiness;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canRetrieveByIdWhenNoSettingId() {
        // when
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/jukeboxes", Object.class);

        // then
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void canRetrieveByIdWhenExistsOnlySettingId() {
        String settingId = "testId";
        List<JukeBox> jukeBoxList = new ArrayList<>();
        JukeBoxComponent jukeBoxComponent1 = new JukeBoxComponent("component1");
        JukeBox jukeBox1 = new JukeBox("id1", "model1", Arrays.asList(jukeBoxComponent1));
        jukeBoxList.add(jukeBox1);

        // given
        given(jukeBoxBusiness.getJukeBoxesBySettings(settingId, null, null, null))
                .willReturn(jukeBoxList);

        // when
        String uri = String.format("/jukeboxes?settingId=%s", settingId);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);

        // then
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<JukeBox> responseList = (List<JukeBox>)responseEntity.getBody();
        Assert.assertEquals(responseList.size(), 1);
    }

    @Test
    public void canRetrieveByIdWhenExistsAllParameters() {
        String settingId = "testId";
        String jukeBoxModel = "model1";
        Integer offset = 0;
        Integer limit = 10;
        List<JukeBox> jukeBoxList = new ArrayList<>();
        JukeBoxComponent jukeBoxComponent1 = new JukeBoxComponent("component1");
        JukeBox jukeBox1 = new JukeBox("id1", "model1", Arrays.asList(jukeBoxComponent1));
        jukeBoxList.add(jukeBox1);

        // given
        given(jukeBoxBusiness.getJukeBoxesBySettings(settingId, jukeBoxModel, offset, limit))
                .willReturn(jukeBoxList);

        // when
        String uri = String.format("/jukeboxes?settingId=%s&model=%s&offset=%d&limit=%d", settingId, jukeBoxModel, offset, limit);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);

        // then
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<JukeBox> responseList = (List<JukeBox>)responseEntity.getBody();
        Assert.assertEquals(responseList.size(), 1);
    }

    @Test
    public void canRetrieveByIdWhenNotFound() {
        String settingId = "testId";
        String jukeBoxModel = "model1";
        Integer offset = 0;
        Integer limit = 10;

        // given
        given(jukeBoxBusiness.getJukeBoxesBySettings(settingId, jukeBoxModel, offset, limit))
                .willThrow(new EntityNotFoundException(JukeBoxSetting.class, "id", settingId));

        // when
        String uri = String.format("/jukeboxes?settingId=%s&model=%s&offset=%d&limit=%d", settingId, jukeBoxModel, offset, limit);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);

        // then
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(responseEntity.getBody().toString(), "{status=NOT_FOUND}");
    }
}
