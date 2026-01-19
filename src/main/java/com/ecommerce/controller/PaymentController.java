package com.ecommerce.controller;

import com.ecommerce.dto.CreatePaymentRequest;
import com.ecommerce.model.Payment;
import com.ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public Payment createPayment(@RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(
                request.getOrderId(),
                request.getAmount()
        );
    }
}
