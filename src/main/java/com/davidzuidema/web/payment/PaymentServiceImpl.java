package com.davidzuidema.web.payment;

import org.springframework.stereotype.Component;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.security.Customer;
import com.davidzuidema.web.security.PaymentRules;
import com.davidzuidema.web.validation.Validate;

@Component
public class PaymentServiceImpl implements PaymentService {

	@Override
	public Result<PaymentDto> scheduleSinglePayment(Customer customer, ScheduleSinglePaymentRequest request) {
		return Result
				.of(request)
				.flatMap(Validate::bean)
				.flatMap(PaymentRules.hasAccess(customer))
				.map(PaymentAdapter::toEntity)
				.map(PaymentAdapter::fromEntity);
	}

}
