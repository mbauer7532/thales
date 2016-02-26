package thales.utils;

import java.util.Objects;

/**
 * Created by Neo on 2/26/2016.
 */

public final class RefModule {

    public static final class Ref<A> {
        public static <A> Ref<A> of(final A a) {
            return new Ref<>(a);
        }

        public Ref(final A a) {
            r = Objects.requireNonNull(a);
        }

        public A r;

        @Override
        public String toString() {
            return String.format("Ref(%s)", r.toString());
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Ref) {
                @SuppressWarnings("unchecked")
                final Ref<A> ref = (Ref<A>) obj;
                return r.equals(ref.r);
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 47 + r.hashCode();
        }
    }

    public static final class IntRef {
        public static IntRef of(final int nv) {
            return new IntRef(nv);
        }

        public IntRef(final int nv) {
            n = nv;
        }

        public int n;

        @Override
        public String toString() {
            return String.format("IntRef(%d)", n);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof IntRef) {
                @SuppressWarnings("unchecked")
                final IntRef ref = (IntRef) obj;
                return n == ref.n;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(n);
        }
    }

    public static final class DoubleRef {
        public static DoubleRef of(final double d) {
            return new DoubleRef(d);
        }

        private DoubleRef(final double dv) {
            d = dv;
        }

        public double d;

        @Override
        public String toString() {
            return String.format("DoubleRef(%f)", d);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof DoubleRef) {
                @SuppressWarnings("unchecked")
                final DoubleRef ref = (DoubleRef) obj;
                return d == ref.d;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Double.hashCode(d);
        }
    }
}
