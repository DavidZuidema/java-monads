package com.davidzuidema.web.monad;

import java.util.function.Function;

/**
 * class Functor f
 */
public interface Functor<A> {
	/**
	 * fmap :: (a -> b) -> f a -> f b
	 */
	public <B> Functor<B> map(Function<A, B> f);

	static <T> T id(T t) {
		return t;
	}
}
