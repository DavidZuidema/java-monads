package com.davidzuidema.web.monad;

import java.util.function.Function;

/**
 * class Functor f
 */
public interface Functor<A> {
	/**
	 * fmap :: f a -> (a -> b) -> f b
	 */
	public <B> Functor<B> map(Function<A, B> f);
}
