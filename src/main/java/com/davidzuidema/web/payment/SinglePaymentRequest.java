package com.davidzuidema.web.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class SinglePaymentRequest {
	private String customerId;
	private BigDecimal amount;
	private LocalDate date;
}
