package com.davidzuidema.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.PaymentDto;
import com.davidzuidema.web.payment.PaymentService;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	@Mock
	PaymentService paymentService;

	@InjectMocks
	PaymentController subject;

	@Test
	public void schedulePayment() {
		ScheduleSinglePaymentRequest request = new ScheduleSinglePaymentRequest();
		Result<PaymentDto> response = Result.of(new PaymentDto());
		when(paymentService.scheduleSinglePayment(request)).thenReturn(response);

		Result<PaymentDto> actual = subject.schedulePayment(request);

		verify(paymentService).scheduleSinglePayment(request);
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void helloWorld() throws Exception {
		assertThat(subject.helloWorld(), is("Hello, World!"));
	}
}
