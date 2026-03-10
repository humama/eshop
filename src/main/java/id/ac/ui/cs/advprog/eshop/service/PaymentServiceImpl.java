package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                method,
                "PENDING",
                paymentData
        );

        if (method.equalsIgnoreCase("VOUCHER")) {

            String voucherCode = paymentData.get("voucherCode");

            if (isValidVoucher(voucherCode)) {
                payment.setStatus("SUCCESS");
                order.setStatus(OrderStatus.SUCCESS.getValue());
            } else {
                payment.setStatus("REJECTED");
                order.setStatus(OrderStatus.FAILED.getValue());
            }

        } else if (method.equalsIgnoreCase("COD")) {

            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");

            if (isEmpty(address) || isEmpty(deliveryFee)) {
                payment.setStatus("REJECTED");
                order.setStatus(OrderStatus.FAILED.getValue());
            } else {
                payment.setStatus("SUCCESS");
                order.setStatus(OrderStatus.SUCCESS.getValue());
            }
        }

        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private boolean isValidVoucher(String voucherCode) {

        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        long digitCount = voucherCode.chars()
                .filter(Character::isDigit)
                .count();

        return digitCount == 8;
    }
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}