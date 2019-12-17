package org.jonathas.musicbox.business;

import org.jonathas.musicbox.exceptions.EntityNotFoundException;
import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxComponent;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.jonathas.musicbox.service.JukeBoxServiceMocked;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JukeBoxBusinessTest {

    @MockBean
    JukeBoxServiceMocked jukeBoxServiceMocked;

    @Autowired
    JukeBoxBusiness jukeBoxBusiness;

    private List<JukeBoxSetting> jukeBoxSettingList;
    private List<JukeBox> jukeBoxList;

    @Before
    public void setUp() {
        // List jukeboxSetting
        jukeBoxSettingList = new ArrayList<>();
        JukeBoxSetting jukeBoxSetting1 = new JukeBoxSetting("id1", Arrays.asList("comp1", "comp2"));
        JukeBoxSetting jukeBoxSetting2 = new JukeBoxSetting("id2", Arrays.asList("comp2", "comp3"));
        jukeBoxSettingList.add(jukeBoxSetting1);
        jukeBoxSettingList.add(jukeBoxSetting2);

        // List jukebox
        jukeBoxList = new ArrayList<>();
        JukeBoxComponent component1 = new JukeBoxComponent("comp1");
        JukeBoxComponent component2 = new JukeBoxComponent("comp2");
        JukeBoxComponent component3 = new JukeBoxComponent("comp3");

        jukeBoxList.add(new JukeBox("id1", "model1", Arrays.asList(component1, component2)));
        jukeBoxList.add(new JukeBox("id2", "model2", Arrays.asList(component1, component2)));
        jukeBoxList.add(new JukeBox("id3", "model3", Arrays.asList(component1)));
        jukeBoxList.add(new JukeBox("id4", "model4", Arrays.asList(component1, component2, component3)));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getJukeBoxesBySettings_SettingIdNotFound() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);

        // when
        jukeBoxBusiness.getJukeBoxesBySettings("fake", null, null, null);
    }

    @Test
    public void getJukeBoxesBySettings_EmptyJukeBoxList() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(new ArrayList<JukeBox>());

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, null, null);

        // then
        Assert.assertTrue(jukeBoxList.isEmpty());
    }

    @Test
    public void getJukeBoxesBySettings_FullResult() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, null, null);

        // then
        Assert.assertEquals(jukeBoxList.size(), 3);
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id1").findFirst().isPresent());
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id2").findFirst().isPresent());
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id4").findFirst().isPresent());
    }

    @Test
    public void getJukeBoxesBySettings_FilterByModel() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", "model2", null, null);

        // then
        Assert.assertEquals(jukeBoxList.size(), 1);
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id2").findFirst().isPresent());
    }

    @Test
    public void getJukeBoxesBySettings_Offset() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, 1, null);

        // then
        Assert.assertEquals(jukeBoxList.size(), 2);
    }

    @Test
    public void getJukeBoxesBySettings_Limit() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, null, 1);

        // then
        Assert.assertEquals(jukeBoxList.size(), 1);
    }

    @Test
    public void getJukeBoxesBySettings_OffsetAndLimit() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, 1, 2);

        // then should return two element (indexes 1 and 2)
        Assert.assertEquals(jukeBoxList.size(), 2);
    }

    @Test
    public void getJukeBoxesBySettings_OffsetOutOfBounds() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, 10, 2);

        // then should return a empty list
        Assert.assertTrue(jukeBoxList.isEmpty());
    }

    @Test
    public void getJukeBoxesBySettings_LimitOutOfBounds() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", null, null, 20);

        // then should return the full list
        Assert.assertEquals(jukeBoxList.size(), 3);
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id1").findFirst().isPresent());
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id2").findFirst().isPresent());
        Assert.assertTrue(jukeBoxList.stream().filter(jukeBox -> jukeBox.getId() == "id4").findFirst().isPresent());
    }

    @Test
    public void getJukeBoxesBySettings_FullParemeters() {
        // given
        given(jukeBoxServiceMocked.getSettingsList()).willReturn(jukeBoxSettingList);
        given(jukeBoxServiceMocked.getJukeBoxList()).willReturn(jukeBoxList);

        // when
        List<JukeBox> jukeBoxList = jukeBoxBusiness.getJukeBoxesBySettings("id1", "model2", 0, 10);

        // then
        Assert.assertEquals(jukeBoxList.size(), 1);
        Assert.assertEquals(jukeBoxList.get(0).getId(), "id2");
        Assert.assertEquals(jukeBoxList.get(0).getModel(), "model2");
        Assert.assertEquals(jukeBoxList.get(0).getComponents().size(), 2);
    }
}
