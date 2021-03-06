package pl.biometria.voice.recognition.features;


import pl.biometria.voice.recognition.algorithms.LinearPredictiveCoding;
import pl.biometria.voice.recognition.algorithms.windowing.HammingWindowFunction;
import pl.biometria.voice.recognition.algorithms.windowing.WindowFunction;

public class LpcFeaturesExtractor extends WindowedFeaturesExtractor<double[]> {

  private final int poles;
  private final WindowFunction windowFunction;
  private final LinearPredictiveCoding lpc;

  public LpcFeaturesExtractor(float sampleRate, int poles) {
    super(sampleRate);
    this.poles = poles;
    this.windowFunction = new HammingWindowFunction(windowSize);
    this.lpc = new LinearPredictiveCoding(windowSize, poles);
  }

  @Override
  public double[] extractFeatures(double[] voiceSample) {

    double[] voiceFeatures = new double[poles];
    double[] audioWindow = new double[windowSize];

    int counter = 0;
    int halfWindowLength = windowSize / 2;

    // poruszamy sie o polowe dlugosci okna
    for (int i = 0; (i + windowSize) <= voiceSample.length; i += halfWindowLength) {

      System.arraycopy(voiceSample, i, audioWindow, 0, windowSize);

      // IMPORTANT: poniższy kod modyfikuje zawartośc tablicy audioWindow
      windowFunction.applyFunction(audioWindow);
      double[] lpcCoeffs = lpc.applyLinearPredictiveCoding(audioWindow)[0];

      for (int j = 0; j < poles; j++) {
        voiceFeatures[j] += lpcCoeffs[j];
      }
      counter++;
    }

    if (counter > 1) {
      for (int i = 0; i < poles; i++) {
        voiceFeatures[i] /= counter;
      }
    }
    return voiceFeatures;
  }
}
