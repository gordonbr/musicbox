package org.jonathas.musicbox.business;

import org.jonathas.musicbox.exceptions.EntityNotFoundException;
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

    /**
     * Looks for the jukeboxes that support a given setting
     * @param settingId The setting id
     * @param model Filter the jukeboxes by model
     * @param offset Offset of the returning list
     * @param limit Limit of the returning list
     * @return A list of Jukeboxes that support the setting
     * @throws EntityNotFoundException if no setting was found for the given setting id
     */
    public List<JukeBox> getJukeBoxesBySettings(String settingId, String model, Integer offset, Integer limit) {

        List<JukeBoxSetting> jukeBoxSettings = jukeBoxService.getSettingsList();
        JukeBoxSetting jukeBoxSetting = findJukeBoxSettings(jukeBoxSettings, settingId);

        List<JukeBox> jukeBoxList = jukeBoxService.getJukeBoxList();

        return filterJukerBoxes(jukeBoxList, jukeBoxSetting, settingId, model, offset, limit);
    }

    /**
     * Looks for the JukeboxSetting with the given setting id.
     * @param jukeBoxSettingList List of JukeboxSetting
     * @param settingId Setting id to be matched against
     * @return The jukeboxSetting with the given setting id or throws a exception with none was found.
     * @throws EntityNotFoundException if no setting was found for the given setting id
     */
    private JukeBoxSetting findJukeBoxSettings(List<JukeBoxSetting> jukeBoxSettingList, String settingId) {
        Optional<JukeBoxSetting> optionalJukeBoxSetting = jukeBoxSettingList.stream().filter(setting -> setting.getId().equals(settingId)).findFirst();
        return optionalJukeBoxSetting.orElseThrow(() -> new EntityNotFoundException(JukeBoxBusiness.class, "id", settingId));
    }

    /**
     * Look for the jukeboxes that have all the components in a given jukeboxSetting
     * @param jukeBoxes A list of jukebox
     * @param jukeBoxSetting a jukeboxSetting
     * @param settingId the jukebox setting id to be matched agains
     * @param model filter the returning list by jukebox model
     * @param offset offset of the returning list
     * @param limit limit of the returning list
     * @return A list of jukebox that match the given parameters
     */
    private List<JukeBox> filterJukerBoxes(List<JukeBox> jukeBoxes, JukeBoxSetting jukeBoxSetting, String settingId,
                                           String model, Integer offset, Integer limit) {

        List<JukeBox> foundJukeBoxes = new ArrayList<>();
        foundJukeBoxes = jukeBoxes.stream().filter(jukeBox -> { // filter by list of component
            // If a given required component in a setting is not present, returns false
            for(String component : jukeBoxSetting.getRequires()) {
                if (!jukeBoxHasComponent(jukeBox, component)) { return false; }
            }

            // Given the jukebox has all component, filter by model, if present
            if(model != null) {
                return jukeBox.getModel().equals(model);
            } else { return true; }
        }).collect(Collectors.toList());

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
