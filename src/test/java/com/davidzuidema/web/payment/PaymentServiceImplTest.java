package com.davidzuidema.web.payment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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
		subject.scheduleSinglePayment(new ScheduleSinglePaymentRequest());

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ScheduleSinglePaymentRequest>> results = validator
				.validate(new ScheduleSinglePaymentRequest());
		assertThat(results.size(), is(1));
		System.out.println(results.stream().map(v -> v.getMessage()).collect(Collectors.toList()));
	}
}
