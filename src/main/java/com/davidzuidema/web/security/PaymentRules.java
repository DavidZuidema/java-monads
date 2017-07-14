package com.davidzuidema.web.security;

import java.util.function.Function;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

public class PaymentRules {
	/**
	 * <pre>
	 * User -> ScheduleSindlePaymentRequest -> Result&lt;ScheduleSinglePaymentRequest&gt;
	 * </pre>
	 */
	public static Function<ScheduleSinglePaymentRequest, Result<ScheduleSinglePaymentRequest>> canScheduleSinglePayment(
			final Customer customer) {
		return request -> request.getCustomerId().equals(customer.getId())
				? Result.success(request)
				: Result.error("Unauthorized Action");
	}
}
