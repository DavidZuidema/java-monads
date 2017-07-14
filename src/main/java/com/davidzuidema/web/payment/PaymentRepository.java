package com.davidzuidema.web.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentRepository {
	PaymentDao save(PaymentDao payment) {
		return payment;
	}
}
