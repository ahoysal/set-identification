public class Blurer {
    public static int[][] applyBlur(int[][] original, int blurSize) {
        int[][] output = new int[original.length][original[0].length];
        blurSize = blurSize * 2 + 1;

        int krbs = blurSize / 2, kcbs = blurSize / 2; // k(r/c)bs = kernel row/column border size

        for (int r = krbs; r < original.length - krbs; r++) {
            for (int c = kcbs; c < original[r].length - kcbs; c++) {
                output[r][c] = applyBlur(original, r, c, blurSize, krbs, kcbs);
            }
        }

        return output;
    }

    private static int applyBlur(int[][] original, int r, int c, int blurSize, int krbs, int kcbs) {
        int accumulated = 0;
        Integer count = 0;
        for (int i = 0; i < blurSize; i++) {
            for (int j = 0; j < blurSize; j++) {
                accumulated = Color.averageColors(accumulated, original[r - krbs + i][c - kcbs + j], count);
                count++;
            }
        }

        // clamp
        return accumulated;
    }
}
