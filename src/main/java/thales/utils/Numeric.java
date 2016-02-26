package thales.utils;

import java.math.BigDecimal;

/**
 * Created by Neo on 2/26/2016.
 */

import thales.utils.TupleModule.Pair;
import java.math.BigDecimal;

/**
 *
 * @author nmichael
 */
public final class Numeric {

    public static double toFiniteDouble(final double x) { return Double.isFinite(x) ? x : 0.0; }

    public static int regDoubleComparator(final double v0, final double v1) {
        return Double.compare(v0, v1);
    }

    public static int absDoubleComparator(final double v0, final double v1) {
        return Double.compare(Math.abs(v0), Math.abs(v1));
    }

    public static double add(final double x, final double y) {
        return x + y;
    }

    public static double mult(final double x, final double y) {
        return x * y;
    }

    public static double sub(final double x, final double y) {
        return x - y;
    }

    public static int inc(final int n) {
        return n + 1;
    }

    public static int dec(final int n) {
        return n - 1;
    }
    /**
     * Returns the sign of the integer value.
     *
     * @param n Integer variable
     * @return <ul>
     *     <li> -1 When  n < 0</li>
     *     <li> 0  When  n = 0</li>
     *     <li> 1  When  n > 0</li>
     * </ul>
     */
    public static int signum(final int n) {

        return n < 0 ? -1 : n > 0 ? 1 : 0;
    }

    /**
     * Returns the sign of the double value.
     *
     * @param n Double variable
     * @return <ul>
     *     <li> -1 When  n < 0.0</li>
     *     <li> 0  When  n = 0.0</li>
     *     <li> 1  When  n > 0.0</li>
     * </ul>
     */
    public static int signum(final double n) {
        return n < 0.0 ? -1 : n > 0.0 ? 1 : 0;
    }

    public static long combineToLong(final int x, final int y) {

        final long s = x;
        final long m = ((long) y) & 0xFFFFFFFFL;

        return (s << 32) | m;
    }

    public static Pair<Integer, Integer> uncombineFromLong(final long val) {

        final int x = (int) ((val >> 32) & 0xFFFFFFFFL);
        final int y = (int) (val & 0xFFFFFFFFL);

        return Pair.create(x, y);
    }

    public static int indicator(final boolean flag) {
        return flag ? 1 : 0;
    }

    public static boolean invIndicator(final int n) {
        return n != 0;
    }

    public static int min(final int n0, final int n1, final int n2) {
        return Math.min(Math.min(n0, n1), n2);
    }

    public static int max(final int n0, final int n1, final int n2) {
        return Math.max(Math.max(n0, n1), n2);
    }

    public static double positive(final double z) {
        return Math.max(z, 0.0);
    }

    public static double negative(final double z) {
        return - Math.min(z, 0.0);
    }

    /**
     * See {@link java.math.BigDecimal#ROUND_HALF_UP ROUND_HALF_UP}.
     *
     * @param val Value to round
     * @param digits Digits to round to
     * @return The rounded value
     */
    public static double roundHalfUp(final double val, final int digits) {
        return new BigDecimal(val).setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static int hashCombine(final int seed, final int v) {
        return seed ^ (v + 0x9e3779b9 + (seed << 6) + (seed >> 2));
    }

    private Numeric() {}
}