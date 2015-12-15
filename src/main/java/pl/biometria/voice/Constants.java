package pl.biometria.voice;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final float AUDIO_SAMPLE_RATE = 22000.0f;
    public static final long RECORD_TIME = TimeUnit.SECONDS.toMillis(2);
    public static final String AUDIO_SAMPLE_NAME = "RecordAudio.wav";
    public static final String DB_FILENAME = "db";
}
