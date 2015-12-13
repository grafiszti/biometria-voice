package pl.biometria.voice.player;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class WavPlayer {
  Clip clip;

  public WavPlayer(String wavFileName) {
    this.clip = readClipFromFileName(wavFileName);
  }

  public WavPlayer(File wavFile) {
    this.clip = openWaveFile(wavFile);
  }

  private Clip readClipFromFileName(String fileName) {
    File file = new File(fileName);
    return openWaveFile(file);
  }

  private Clip openWaveFile(File file) {
    try {
      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      AudioFormat format = stream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.open(stream);
      return clip;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void play() {
    clip.start();
  }
}
