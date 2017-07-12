package com.davidzuidema.web.payment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

	@InjectMocks
	PaymentServiceImpl subject;

	@Test
	public void scheduleSinglePayment() throws Exception {
		subject.scheduleSinglePayment(new SinglePaymentRequest());
	}
}
