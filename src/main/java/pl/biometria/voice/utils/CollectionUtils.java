package pl.biometria.voice.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import pl.biometria.voice.recognition.VoicePrint;
import pl.biometria.voice.recognition.utils.NamedVoicePrint;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionUtils {
    public static Map<String, VoicePrint> convertNamedVoicePrintsToMap(List<NamedVoicePrint> namedVoicePrints) {
        Map<String, VoicePrint> resultMap = Maps.newHashMap();
        for (NamedVoicePrint nvp : namedVoicePrints) {
            resultMap.put(nvp.getName(), nvp.getVoicePrint());
        }
        return resultMap;
    }

    public static List<NamedVoicePrint> convertMapNameVoicePrintToNamedVoicePrintList(Map<String, VoicePrint> voicePrints) {
        List<NamedVoicePrint> resultList = Lists.newArrayList();
        for (Entry<String, VoicePrint> entry : voicePrints.entrySet()) {
            resultList.add(new NamedVoicePrint(entry.getKey(), entry.getValue()));
        }
        return resultList;
    }
}
