package com.davidzuidema.web.monad;

import java.util.function.Function;

/**
 * class Functor f => Applicative f
 */
public interface Applicative<A> extends Functor<A> {
	/**
	 * (<*>) :: f a -> f (a -> b) -> f b
	 */
	<B> Applicative<B> seqApply(Applicative<Function<A, B>> f);
}
