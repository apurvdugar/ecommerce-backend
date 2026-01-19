package com.ecommerce.webhook;

import com.ecommerce.dto.PaymentWebhookRequest;
import com.ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public String handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        paymentService.processWebhook(request);
        return "Webhook processed successfully";
    }
}
