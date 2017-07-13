package com.davidzuidema.web;

import java.util.function.Consumer;

public class Util {
	public static <T> T create(T t, Consumer<T> f) {
		f.accept(t);
		return t;
	}
}
