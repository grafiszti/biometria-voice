package pl.biometria.voice.recognitio.utils;

import com.bitsinharmony.recognito.VoicePrint;

public class NamedVoicePrint {
  String name;
  VoicePrint voicePrint;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public VoicePrint getVoicePrint() {
    return voicePrint;
  }

  public void setVoicePrint(VoicePrint voicePrint) {
    this.voicePrint = voicePrint;
  }
}
