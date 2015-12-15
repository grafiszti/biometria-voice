package pl.biometria.voice.features;


public interface FeaturesExtractor<T> {

    public T extractFeatures(double[] voiceSample);

}