package SortVisualisation.Model;
public class RandomGen {
    public static int[] generateRandomInts(int max) {
        int[] intSeries = new int[max];
        for(int i=0; i<max; i++) {
            intSeries[i] = (int) (Math.random() * max + 1);
        }
        return intSeries;
    }
}
