package com.davidzuidema.web.security;

import java.time.Duration;
import java.time.LocalDate;
import java.util.function.Function;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

public class PaymentRules {
	/**
	 * Security Rule:
	 * Request data must match logged in user.
	 *
	 * Curried to allow for request processing composition.
	 *
	 * This method takes the current customer
	 * and returns a partially applied function
	 * which takes a request and checks whether
	 * the request data matches the Customer.
	 */
	public static Function<ScheduleSinglePaymentRequest, Result<ScheduleSinglePaymentRequest>> hasAccess(
			final Customer customer) {
		return request -> request.getCustomerId().equals(customer.getId())
				? Result.success(request)
				: Result.error("Unauthorized Action");
	}

	/**
	 * Business Rule:
	 * Payments must be scheduled at least two days in advance of their payment date
	 */
	public static Result<ScheduleSinglePaymentRequest> isAfterFreeze(
			ScheduleSinglePaymentRequest request) {
		LocalDate today = LocalDate.now();
		long daysInAdvance = Duration.between(today.atStartOfDay(), request.getDate().atStartOfDay()).toDays();
		return daysInAdvance < 2
				? Result.error("Must schedule payments at least TWO days in advance")
				: Result.success(request);
	}
}
