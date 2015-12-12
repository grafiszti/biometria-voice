package pl.biometria.voice;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.biometria.voice.gui.MainWindow;

import com.badlogic.gdx.audio.analysis.FFT;
import com.badlogic.gdx.audio.io.WavDecoder;
import com.badlogic.gdx.files.FileHandle;
import com.google.common.collect.Lists;

public class Main {
  // record duration, in milliseconds
  static final Integer RECORD_TIME = 1000; // 1 minute

  public static void main(String[] args) {
    MainWindow.run();

    final Recorder recorder = new Recorder();

    // creates a new thread that waits for a specified
    // of time before stopping
    Thread stopper = new Stopper(RECORD_TIME, recorder);
    stopper.start();

    // start recording
    recorder.start();

    while (recorder.isRecording()) {
    }

    File asd = recorder.getAudioFile();
    WavDecoder wavDecoder = new WavDecoder(new FileHandle(asd));

    System.out.println(wavDecoder.getRate());
    System.out.println(wavDecoder.readAllSamples().length);

    FFT fft = new FFT(1024, wavDecoder.getRate());

    float[] samples = new float[1024];
    float[] spectrum = new float[1024 / 2 + 1];
    float[] lastSpectrum = new float[1024 / 2 + 1];
    List<Float> spectralFlux = new ArrayList<Float>();

    Iterator it = Lists.newArrayList(wavDecoder.readAllSamples()).iterator();
    while (it.hasNext()) {
      it.next();
      fft.forward(samples);
      System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
      System.arraycopy(fft.getSpectrum(), 0, spectrum, 0, spectrum.length);

      float flux = 0;
      for (int i = 0; i < spectrum.length; i++)
        flux += (spectrum[i] - lastSpectrum[i]);
      spectralFlux.add(flux);
    }

    int asda = 3;
    asda = +123;
    System.out.println("panu juz podziekujemy");
  }
}
