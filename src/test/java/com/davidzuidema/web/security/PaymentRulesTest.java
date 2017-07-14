package com.davidzuidema.web.security;

import static com.davidzuidema.web.Util.create;
import static com.davidzuidema.web.monad.ResultMatchers.failedAndError;
import static com.davidzuidema.web.monad.ResultMatchers.succeededAndValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

@RunWith(MockitoJUnitRunner.class)
public class PaymentRulesTest {

	Customer customer;

	@Before
	public void before() {
		customer = create(new Customer(), c -> c.setId("1"));
	}

	@Test
	public void hasAccess_withWrongCustomerId_returnsError() throws Exception {
		ScheduleSinglePaymentRequest request = create(new ScheduleSinglePaymentRequest(), r -> r.setCustomerId("2"));

		Result<ScheduleSinglePaymentRequest> result = PaymentRules.hasAccess(customer).apply(request);

		assertThat(result, failedAndError(is("Unauthorized Action")));
	}

	@Test
	public void hasAccess_withMatchingCustomerId_returnsSuccess() throws Exception {
		ScheduleSinglePaymentRequest request = create(new ScheduleSinglePaymentRequest(), r -> r.setCustomerId("1"));

		Result<ScheduleSinglePaymentRequest> result = PaymentRules.hasAccess(customer).apply(request);

		assertThat(result, succeededAndValue(is(sameInstance(request))));
	}
}
