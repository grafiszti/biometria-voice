package pl.biometria.voice.recognition;

/**
 * MatchResult represents the result of matching a <code>VoicePrint</code> against a given voice sample.
 * It holds the user defined key of the voice print and a likelihood ratio expressed as a percentage.
 *
 * @param <K> The type of the key selected by the user
 */
public class MatchResult<K> {

    private final K key;
    private final int likelihoodRatio;
    private final double distance;

    /**
     * Default constructor
     *
     * @param key             the user defined key for the corresponding VoicePrint
     * @param likelihoodRatio the likelihood ratio expressed as a percentage
     */
    MatchResult(K key, int likelihoodRatio, double distance) {
        super();
        this.key = key;
        this.likelihoodRatio = likelihoodRatio;
        this.distance = distance;
    }

    /**
     * Get the matched key
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the likelihoodRatio level
     *
     * @return the likelihoodRatio ratio expressed as a percentage
     */
    public int getLikelihoodRatio() {
        return likelihoodRatio;
    }

    /**
     * Get the raw distance between the <code>VoicePrint</code> idenntified by K
     * and the given voice sample
     *
     * @return the distance
     */
    double getDistance() {
        return distance;
    }
}