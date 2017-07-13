package com.davidzuidema.web.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ScheduleSinglePaymentRequest {
	@NotNull(message = "Customer Id Required")
	private String customerId;
	private BigDecimal amount;
	private LocalDate date;
}
