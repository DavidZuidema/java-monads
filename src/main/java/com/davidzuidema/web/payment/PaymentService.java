package com.davidzuidema.web.payment;

public interface PaymentService {
	SinglePaymentResponse scheduleSinglePayment(SinglePaymentRequest request);
}
