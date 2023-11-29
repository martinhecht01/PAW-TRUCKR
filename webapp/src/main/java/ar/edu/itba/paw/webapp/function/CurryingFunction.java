package ar.edu.itba.paw.webapp.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface CurryingFunction<T,U,R> extends BiFunction<T,U,R> {
    default Function<U, R> curry(final BiFunction<T, U, R> f, T param) {
        return new Function<U, R>() {
            @Override
            public R apply(U u) {
                return f.apply(param, u);
            }
        };
    }
}
