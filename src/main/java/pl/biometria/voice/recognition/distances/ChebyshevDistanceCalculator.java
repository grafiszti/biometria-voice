package pl.biometria.voice.recognition.distances;

/**
 * Chebyshev distance implementation (a.k.a Chessboard/Manhattan)
 * <p>
 * Threading : this class is thread safe
 * </p>
 *
 * @see <a href="http://en.wikipedia.org/wiki/Chebyshev_distance">Chebyshev distance</a>
 */
public class ChebyshevDistanceCalculator
        extends DistanceCalculator {

    /**
     * Chebyshev Distance implementation.
     * Both features must have the same length
     *
     * @param features1 first vector to compare
     * @param features2 second vector to compare
     * @return Chebyshev distance between two feature vectors
     */
    public double getDistance(double[] features1, double[] features2) {
        double distance = positiveInfinityIfEitherOrBothAreNull(features1, features2);
        if (distance < 0) {
            if (features1.length != features2.length) {
                throw new IllegalArgumentException("Both features should have the same length. Received lengths of [" +
                        +features1.length + "] and [" + features2.length + "]");
            }
            distance = 0.0;
            for (int i = 0; i < features1.length; i++) {
                double currentDistance = Math.abs(features1[i] - features2[i]);
                distance = (currentDistance > distance) ? currentDistance : distance;
            }
        }
        return distance;
    }

}
