package com.davidzuidema.web.monad;

import java.util.Objects;
import java.util.function.Function;

public final class Result<A> {

	private final A value;
	private final String error;

	private Result(A value, String error) {
		this.value = value;
		this.error = error;
	}

	public static <T> Result<T> of(T value) {
		return success(value);
	}

	public static <T> Result<T> success(T value) {
		Objects.requireNonNull(value);
		return new Result<>(value, null);
	}

	public static <T> Result<T> error(String message) {
		Objects.requireNonNull(message);
		return new Result<>(null, message);
	}

	public boolean succeeded() {
		return this.value != null;
	}

	public boolean failed() {
		return this.value == null;
	}

	public A extract() {
		if (value == null) {
			throw new IllegalStateException("No value present");
		}
		return this.value;
	}

	public String error() {
		if (error == null) {
			throw new IllegalStateException("No error present");
		}
		return this.error;
	}

	public <B> Result<B> map(Function<A, B> f) {
		Objects.requireNonNull(f);
		if (failed()) {
			return error(error);
		}
		return success(f.apply(value));
	}

	public <B> Result<B> flatMap(Function<A, Result<B>> f) {
		Objects.requireNonNull(f);
		if (failed()) {
			return error(error);
		}
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

		if (other.succeeded() != succeeded()) {
			return false;
		}

		if (failed()) {
			Objects.equals(error, other.error);
		}
		return Objects.equals(value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return value != null ? String.format("Success[%s]", value) : String.format("Fail[%s]", error);
	}

}
