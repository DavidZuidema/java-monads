package com.davidzuidema.web.monad;

import java.util.function.Function;

/**
 * class Applicative m => Monad m
 */
public interface Monad<T> extends Applicative<T> {
	/**
	 * (>>=) :: m a -> (a -> m b) -> m b
	 */
	<U> Monad<U> bind(Function<T, Monad<U>> f);

	/**
	 * return :: a -> m a
	 */
	default Monad<T> mreturn(T t) {
		return (Monad<T>) lift(t);
	}
}