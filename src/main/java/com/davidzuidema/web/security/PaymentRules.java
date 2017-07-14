package com.davidzuidema.web.security;

import java.time.Duration;
import java.time.LocalDate;
import java.util.function.Function;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

public class PaymentRules {
	/**
	 * <pre>
	 * User -> (ScheduleSindlePaymentRequest -> Result&lt;ScheduleSinglePaymentRequest&gt);
	 * </pre>
	 */
	public static Function<ScheduleSinglePaymentRequest, Result<ScheduleSinglePaymentRequest>> hasAccess(
			final Customer customer) {
		return request -> request.getCustomerId().equals(customer.getId())
				? Result.success(request)
				: Result.error("Unauthorized Action");
	}

	public static Result<ScheduleSinglePaymentRequest> isAfterFreeze(
			ScheduleSinglePaymentRequest request) {
		LocalDate today = LocalDate.now();
		long daysInAdvance = Duration.between(today.atStartOfDay(), request.getDate().atStartOfDay()).toDays();
		return daysInAdvance < 2
				? Result.error("Must schedule payments at least TWO days in advance")
				: Result.success(request);
	}
}
