package pl.biometria.voice.utils;

import java.util.List;
import java.util.Map;

import pl.biometria.voice.recognitio.utils.NamedVoicePrint;

import com.bitsinharmony.recognito.VoicePrint;
import com.google.common.collect.Maps;

public class CollectionUtils {
  public static Map<String, VoicePrint> convertNamedVoicePrintsToMap(List<NamedVoicePrint> namedVoicePrints) {
    Map<String, VoicePrint> resultMap = Maps.newHashMap();
    for (NamedVoicePrint nvp : namedVoicePrints) {
      resultMap.put(nvp.getName(), nvp.getVoicePrint());
    }
    return resultMap;
  }
}
