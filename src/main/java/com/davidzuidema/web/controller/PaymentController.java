package com.davidzuidema.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.davidzuidema.web.monad.Result;
import com.davidzuidema.web.payment.PaymentDto;
import com.davidzuidema.web.payment.PaymentService;
import com.davidzuidema.web.payment.ScheduleSinglePaymentRequest;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Result<PaymentDto> schedulePayment(@RequestBody ScheduleSinglePaymentRequest request) {
		return paymentService.scheduleSinglePayment(request);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String helloWorld() {
		return "Hello, World!";
	}
}
