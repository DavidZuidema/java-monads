package com.davidzuidema.web.monad;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public final class Result<A> implements Functor<A> {

	private final A value;

	private Result() {
		this.value = null;
	}

	private Result(A value) {
		this.value = Objects.requireNonNull(value);
	}

	public static <T> Result<T> of(T t) {
		return new Result<>(t);
	}

	public A get() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return this.value;
	}

	@Override
	public <B> Result<B> map(Function<A, B> f) {
		return Result.of(f.apply(value));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Result)) {
			return false;
		}

		Result<?> other = (Result<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return value != null ? String.format("Result[%s]", value) : "Result.empty";
	}

}
