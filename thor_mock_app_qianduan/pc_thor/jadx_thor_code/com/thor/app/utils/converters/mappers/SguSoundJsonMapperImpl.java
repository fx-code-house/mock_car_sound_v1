package com.thor.app.utils.converters.mappers;

import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundFile;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class SguSoundJsonMapperImpl implements SguSoundJsonMapper {
    @Override // com.thor.app.utils.converters.mappers.SguSoundJsonMapper
    public SguSoundJson getModel(SguSound obj) {
        if (obj == null) {
            return null;
        }
        int id = obj.getId();
        Integer sound_set_id = obj.getSound_set_id();
        String name = obj.getName();
        String image = obj.getImage();
        String description = obj.getDescription();
        List<SguSoundFile> soundFiles = obj.getSoundFiles();
        return new SguSoundJson(id, sound_set_id, name, image, description, obj.getVersion(), soundFiles != null ? new ArrayList(soundFiles) : null, obj.getDriveSelect());
    }

    @Override // com.thor.app.utils.converters.mappers.SguSoundJsonMapper
    public List<SguSoundJson> getModels(List<SguSound> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<SguSound> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getModel(it.next()));
        }
        return arrayList;
    }
}
