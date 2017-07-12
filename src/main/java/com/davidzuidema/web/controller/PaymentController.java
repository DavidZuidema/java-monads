package com.davidzuidema.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.davidzuidema.web.payment.PaymentService;
import com.davidzuidema.web.payment.SinglePaymentRequest;
import com.davidzuidema.web.payment.SinglePaymentResponse;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public SinglePaymentResponse schedulePayment(@RequestBody SinglePaymentRequest request) {
		return paymentService.scheduleSinglePayment(request);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String helloWorld() {
		return "Hello, World!";
	}
}
