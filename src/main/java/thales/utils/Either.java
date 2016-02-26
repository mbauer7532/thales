package thales.utils;

/**
 * Created by Neo on 2/26/2016.
 */

import thales.utils.Functionals.TriConsumer;
import thales.utils.Functionals.TriFunction;
import thales.utils.TupleModule.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author nmichael
 * @param <A> The type of the left projection.
 * @param <B> The type of the right projection.
 */
public class Either<A, B> {

    /**
     * @return a <em>Left</em> Either - the error condition
     */
    public static <A, B> Either<A, B> createLeft(final A left) {
        return Left.create(left);
    }

    public static <A, B> Either<A, B> createRight(final B right) {
        return Right.create(right);
    }

    public static <A, B> Either<A, B> iff(final boolean c, final Supplier<B> right, final Supplier<A> left) {
        return c ? createRight(right.get())
            : createLeft(left.get());
    }

    public boolean isLeft()  { return false; }

    public boolean isRight() { return false; }

    public A getLeft()  {
        throw new RuntimeException("Either<> object was not a left.");
    }

    public B getRight() {
        throw new RuntimeException("Either<> object was not a right.");
    }

    public <W> W apply(final Function<A, W> leftFun, final Function<B, W> rightFun) {
        return isLeft() ? leftFun.apply(getLeft()) : rightFun.apply(getRight());
    }

    public void consume(final Consumer<A> leftConsumer, final Consumer<B> rightConsumer) {
        if (isLeft())
            leftConsumer.accept(getLeft());
        else
            rightConsumer.accept(getRight());
    }

    public static <A, B> Pair<List<A>, List<B>> partitionEithers(final Stream<Either<A, B>> s) {
        final List<A> as = new ArrayList<>();
        final List<B> bs = new ArrayList<>();

        s.forEach(e -> {
            if (e.isLeft()) {
                as.add(e.getLeft());
            }
            else {
                bs.add(e.getRight());
            }
        });

        return Pair.create(as, bs);
    }

    public static <L0, R0, L1, R1, W> W applySuccess(
        final Either<L0, R0> e0,
        final Either<L1, R1> e1,
        final BiFunction<Either<L0, R0>, Either<L1, R1>, W> failureFunctor,
        final BiFunction<R0, R1, W> successFunctor) {

        if (e0.isRight() && e1.isRight()) {
            return successFunctor.apply(e0.getRight(), e1.getRight());
        }
        else {
            return failureFunctor.apply(e0, e1);
        }
    }

    public static <L0, R0, L1, R1, L2, R2, W> W applySuccess(
        final Either<L0, R0> e0,
        final Either<L1, R1> e1,
        final Either<L2, R2> e2,
        final TriFunction<Either<L0, R0>, Either<L1, R1>, Either<L2, R2>, W> failureFunctor,
        final TriFunction<R0, R1, R2, W> successFunctor) {

        if (e0.isRight() && e1.isRight() && e2.isRight()) {
            return successFunctor.apply(e0.getRight(), e1.getRight(), e2.getRight());
        }
        else {
            return failureFunctor.apply(e0, e1, e2);
        }
    }

    public static <L0, R0, L1, R1> void consumeSuccess(
        final Either<L0, R0> e0,
        final Either<L1, R1> e1,
        final BiConsumer<Either<L0, R0>, Either<L1, R1>> failureFunctor,
        final BiConsumer<R0, R1> successFunctor) {

        if (e0.isRight() && e1.isRight()) {
            successFunctor.accept(e0.getRight(), e1.getRight());
        }
        else {
            failureFunctor.accept(e0, e1);
        }
    }

    public static <L0, R0, L1, R1, L2, R2> void consumeSuccess(
        final Either<L0, R0> e0,
        final Either<L1, R1> e1,
        final Either<L2, R2> e2,
        final TriConsumer<Either<L0, R0>, Either<L1, R1>, Either<L2, R2>> failureFunctor,
        final TriConsumer<R0, R1, R2> successFunctor) {

        if (e0.isRight() && e1.isRight() && e2.isRight()) {
            successFunctor.accept(e0.getRight(), e1.getRight(), e2.getRight());
        }
        else {
            failureFunctor.accept(e0, e1, e2);
        }
    }

    static class Left<A, B> extends Either<A, B> {
        static <A, B> Left<A, B> create(final A left) {
            return new Left<>(left);
        }

        private Left(final A left) {
            mLeft = left;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public A getLeft() {
            return mLeft;
        }

        private final A mLeft;
    }

    static class Right<A, B> extends Either<A, B> {
        static <A, B> Right<A, B> create(final B right) {
            return new Right<>(right);
        }

        private Right(final B right) {
            mRight = right;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public B getRight() {
            return mRight;
        }

        private final B mRight;
    }
}
