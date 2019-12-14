package org.jonathas.musicbox.business;

import org.jonathas.musicbox.model.JukeBox;
import org.jonathas.musicbox.model.JukeBoxComponent;
import org.jonathas.musicbox.model.JukeBoxSetting;
import org.jonathas.musicbox.service.JukeBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JukeBoxBusiness {

    @Autowired
    private JukeBoxService jukeBoxService;

    public List<JukeBox> getJukeBoxesBySettings(String settingId, String model, Integer offset, Integer limit) {

        List<JukeBox> jukeBoxList = jukeBoxService.getJukeBoxList();
        List<JukeBoxSetting> jukeBoxSettings = jukeBoxService.getSettingsList();

        return filterJukerBoxes(jukeBoxList, jukeBoxSettings, settingId, model, offset, limit);
    }

    /**
     * Look for the jukeboxes that have all the components in a given settingId
     * @param jukeBoxes A list of jukebox
     * @param jukeBoxSettings a list of jukebox settings
     * @param settingId the jukebox setting id to be matched agains
     * @param model filter the returning list by jukebox model
     * @param offset offset of the returning list
     * @param limit limit of the returning list
     * @return A list of jukebox that match the given parameters
     */
    private List<JukeBox> filterJukerBoxes(List<JukeBox> jukeBoxes, List<JukeBoxSetting> jukeBoxSettings, String settingId,
                                           String model, Integer offset, Integer limit) {

        List<JukeBox> foundJukeBoxes = new ArrayList<>();

        // Look for the JukeBoxSetting with the given settingId
        Optional<JukeBoxSetting> optionalJukeBoxSetting = jukeBoxSettings.stream().filter(setting -> setting.getId().equals(settingId)).findFirst();

        // The JukeBoxSetting for the given settingId was found
        if(optionalJukeBoxSetting.isPresent()) {
            JukeBoxSetting foundJukeBoxSetting = optionalJukeBoxSetting.get();

            foundJukeBoxes = jukeBoxes.stream().filter(jukeBox -> { // filter by list of component

                // If a given required component in a setting is not present, returns false
                for(String component : foundJukeBoxSetting.getRequires()) {
                    if (!jukeBoxHasComponent(jukeBox, component)) { return false; }
                }

                // Given the jukebox has all component, filter by model, if present
                if(model != null) {
                    return jukeBox.getModel().equals(model);
                } else { return true; }

            }).collect(Collectors.toList());
        }

        // Returns the sublist of offset and limit, if present
        return subList(foundJukeBoxes, offset, limit);
    }

    /**
     * Creates a sublist of JukeBoxes based on offset and limit
     * @param jukeBoxList The list of jukeboxes to be created
     * @param offset
     * @param limit
     * @return Returns a sublist based of offset and limit. If offset is null, it will be set to 0.
     * If limit is null or is greater than the list size, it will be set to the size of the original list.
     */
    List<JukeBox> subList(List<JukeBox> jukeBoxList, Integer offset, Integer limit) {
        if (offset == null)
            offset = 0;
        else if (offset > jukeBoxList.size())
            offset = jukeBoxList.size();

        if (limit == null || (offset + limit) > jukeBoxList.size())
            limit = jukeBoxList.size();
        else
            limit = offset + limit;


        return jukeBoxList.subList(offset, limit);
    }

    /**
     * Checks if a jukebox has a given component
     * @param jukeBox Jukebox to be tested
     * @param component Component to be tested against jukebox
     * @return True if the jukebox contains the component, false otherwise
     */
    private boolean jukeBoxHasComponent(JukeBox jukeBox, String component) {
        for(JukeBoxComponent jukeBoxComponent : jukeBox.getComponents()) {
            if(jukeBoxComponent.getName().equals(component))
                return true;
        }
        return false;
    }
}
