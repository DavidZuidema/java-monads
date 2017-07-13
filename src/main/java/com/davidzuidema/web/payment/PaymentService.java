package com.davidzuidema.web.payment;

import com.davidzuidema.web.monad.Result;

public interface PaymentService {
	Result<PaymentDto> scheduleSinglePayment(ScheduleSinglePaymentRequest request);
}
