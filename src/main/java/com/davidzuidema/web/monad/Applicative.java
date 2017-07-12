package com.davidzuidema.web.monad;

import java.util.function.Function;

/**
 * class Functor f => Applicative f
 */
public interface Applicative<T> extends Functor<T> {
	/**
	 * pure :: a -> f a
	 */
	Applicative<T> lift(T t);

	/**
	 * (<*>) :: f (a -> b) -> f a -> f b
	 */
	<U> Applicative<U> seqApply(Applicative<Function<T, U>> f, Applicative<T> x);
}
