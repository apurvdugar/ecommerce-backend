package com.ecommerce.service;

import com.ecommerce.constants.OrderStatus;
import com.ecommerce.constants.PaymentStatus;
import com.ecommerce.dto.PaymentWebhookRequest;
import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(String orderId, Double amount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!OrderStatus.CREATED.equals(order.getStatus())) {
            throw new RuntimeException("Order is not eligible for payment");
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentId("pay_mock_" + UUID.randomUUID());
        payment.setCreatedAt(Instant.now());

        Payment savedPayment = paymentRepository.save(payment);
        final String paymentId = savedPayment.getPaymentId();
        final String finalOrderId = orderId;

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                processWebhook(new PaymentWebhookRequest(finalOrderId, paymentId, PaymentStatus.SUCCESS));
            } catch (InterruptedException ignored) {}
        }).start();

        return savedPayment;
    }


    public void processWebhook(PaymentWebhookRequest request) {

        Payment payment = paymentRepository.findByOrderId(request.getOrderId()).orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setStatus(request.getStatus());
        paymentRepository.save(payment);

        if (PaymentStatus.SUCCESS.equals(request.getStatus())) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
        }
    }
}
