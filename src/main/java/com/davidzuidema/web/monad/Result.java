package com.davidzuidema.web.monad;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public final class Result<A> {

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

	public A extract() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return this.value;
	}

	public <B> Result<B> map(Function<A, B> f) {
		Objects.requireNonNull(f);
		return Result.of(f.apply(value));
	}

	public <B> Result<B> flatMap(Function<A, Result<B>> f) {
		Objects.requireNonNull(f);
		return f.apply(value);
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
