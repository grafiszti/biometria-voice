package pl.biometria.voice.recognition.features;


public interface FeaturesExtractor<T> {

    public T extractFeatures(double[] voiceSample);

}