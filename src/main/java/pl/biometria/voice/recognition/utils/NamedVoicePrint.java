package pl.biometria.voice.recognition.utils;


import pl.biometria.voice.VoicePrint;

import java.io.Serializable;

public class NamedVoicePrint implements Serializable {
    private static final long serialVersionUID = 1574168976921519603L;

    String name;
    VoicePrint voicePrint;

    public NamedVoicePrint(String name, VoicePrint voicePrint) {
        this.name = name;
        this.voicePrint = voicePrint;
    }

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
