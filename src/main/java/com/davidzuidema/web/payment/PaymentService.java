package com.davidzuidema.web.payment;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.security.Customer;

public interface PaymentService {
	Result<PaymentDto> scheduleSinglePayment(Customer customer, ScheduleSinglePaymentRequest request);
}
