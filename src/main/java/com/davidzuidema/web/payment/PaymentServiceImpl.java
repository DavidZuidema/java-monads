package com.davidzuidema.web.payment;

import org.springframework.stereotype.Component;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.validation.Validate;

@Component
public class PaymentServiceImpl implements PaymentService {

	@Override
	public Result<PaymentDto> scheduleSinglePayment(ScheduleSinglePaymentRequest request) {
		return Result
				.of(request)
				.flatMap(Validate::bean)
				.map(PaymentAdapter::toEntity)
				.map(PaymentAdapter::fromEntity);
	}

}
