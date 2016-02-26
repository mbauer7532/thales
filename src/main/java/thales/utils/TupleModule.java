package thales.utils;

import java.util.Objects;

/**
 * Created by Neo on 2/26/2016.
 */

public final class TupleModule {

    public static final class Pair<A, B> {

        private final A mFirst;
        private final B mSecond;

        public static <A, B> Pair<A, B> create(final A first, final B second) {
            return new Pair<>(first, second);
        }

        private Pair(final A first, final B second) {

            mFirst  = Objects.requireNonNull(first);
            mSecond = Objects.requireNonNull(second);
        }

        @Override
        public int hashCode() {
            final int seed = 743129;
            return Numeric.hashCombine(Numeric.hashCombine(seed, mFirst.hashCode()), mSecond.hashCode());
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            final Pair<?, ?> other = (Pair<?, ?>) obj;
            if (!Objects.equals(this.mFirst, other.mFirst)) {
                return false;
            }

            return Objects.equals(this.mSecond, other.mSecond);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", mFirst.toString(), mSecond.toString());
        }

        public A getFirst() {
            return mFirst;
        }

        public B getSecond() {
            return mSecond;
        }
    }

    /**
     * Comparable Pair.
     */
    public static final class CPair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<CPair<A, B>>{

        private final A mFirst;
        private final B mSecond;

        public static <A extends Comparable<A>, B extends Comparable<B>> CPair<A, B> create(final A first, final B second) {

            return new CPair<>(first, second);
        }

        private CPair(final A first, final B second) {

            mFirst  = Objects.requireNonNull(first);
            mSecond = Objects.requireNonNull(second);
        }

        @Override
        public int hashCode() {

            final int seed = 74314567;
            return Numeric.hashCombine(Numeric.hashCombine(seed, mFirst.hashCode()), mSecond.hashCode());
        }

        @Override
        public boolean equals(final Object obj) {

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            final CPair<?, ?> other = (CPair<?, ?>) obj;
            if (!Objects.equals(this.mFirst, other.mFirst))
                return false;

            return Objects.equals(this.mSecond, other.mSecond);
        }

        @Override
        public String toString() { return String.format("(%s, %s)", mFirst.toString(), mSecond.toString()); }

        public A getFirst() { return mFirst; }

        public B getSecond() { return mSecond; }

        @Override
        public int compareTo(final CPair<A, B> o) {

            final int first = getFirst().compareTo(o.getFirst());

            return first != 0 ? first : getSecond().compareTo(o.getSecond());
        }
    }

    public static final class Triplet<A, B, C> {
        private final A mFirst;
        private final B mSecond;
        private final C mThird;

        private Triplet(final A first, final B second, final C third) {
            mFirst  = Objects.requireNonNull(first);
            mSecond = Objects.requireNonNull(second);
            mThird = Objects.requireNonNull(third);
        }

        public static <A, B, C> Triplet<A, B, C> create(final A first, final B second, final C third) {
            return new Triplet<>(first, second, third);
        }

        @Override
        public int hashCode() {
            final int seed = 743129;
            return Numeric.hashCombine(Numeric.hashCombine(Numeric.hashCombine(seed, mFirst.hashCode()), mSecond.hashCode()), mThird.hashCode());
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            final Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
            if (!Objects.equals(this.mFirst, other.mFirst)) {
                return false;
            }

            if (!Objects.equals(this.mSecond, other.mSecond)) {
                return false;
            }

            return Objects.equals(this.mThird, other.mThird);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s)", mFirst.toString(), mSecond.toString(), mThird.toString());
        }

        public A getFirst() {
            return mFirst;
        }

        public B getSecond() {
            return mSecond;
        }

        public C getThird() {
            return mThird;
        }
    }

    /**
     * A Quad.
     */
    public static final class Quad<A, B, C, D> {

        public static <A, B, C, D> Quad<A, B, C, D> create(final A a, final B b, final C c, final D d) {

            return new Quad<>(a, b, c, d);
        }

        public A getFirst() { return mA; }

        public B getSecond() { return mB; }

        public C getThird() { return mC; }

        public D getForth() { return mD; }

        @Override
        public boolean equals(final Object o) {

            if( !(o instanceof Quad) )
                return false;

            final Quad q = (Quad)o;
            return mA.equals(q.mA) && mB.equals(q.mB) && mC.equals(q.mC) && mD.equals(q.mD);
        }

        @Override
        public int hashCode() {

            int hash = 3;
            hash = 97 * hash + Objects.hashCode(mA);
            hash = 97 * hash + Objects.hashCode(mB);
            hash = 97 * hash + Objects.hashCode(mC);
            hash = 97 * hash + Objects.hashCode(mD);
            return hash;
        }

        private Quad(final A a, final B b, final C c, final D d) {

            mA = a;
            mB = b;
            mC = c;
            mD = d;
        }

        private final A mA;
        private final B mB;
        private final C mC;
        private final D mD;
    }
}
