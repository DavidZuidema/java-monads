package com.davidzuidema.web.validation;

import static com.davidzuidema.web.monad.Result.error;
import static com.davidzuidema.web.monad.Result.success;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.davidzuidema.web.monad.Result;

public class Validate {

	public static final Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static <T> Result<T> bean(T t) {
		List<String> errors = getErrors(t);
		return errors.isEmpty() //
				? success(t) //
				: error(String.join(",", errors));
	}

	public static <T> List<String> getErrors(T t) {
		return validator.validate(t)//
				.stream()//
				.map(ConstraintViolation::getMessage)//
				.collect(Collectors.toList());
	}
}
