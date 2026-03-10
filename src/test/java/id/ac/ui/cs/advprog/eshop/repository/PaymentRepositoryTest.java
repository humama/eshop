package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP8765XYZ4321");

        Map<String, String> paymentData3 = new HashMap<>();
        paymentData3.put("voucherCode", "INVALIDCODE");

        Payment payment1 = new Payment(
                "payment-1",
                "VOUCHER",
                paymentData1
        );

        Payment payment2 = new Payment(
                "payment-2",
                "VOUCHER",
                paymentData2
        );

        Payment payment3 = new Payment(
                "payment-3",
                "VOUCHER",
                paymentData3
        );

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {

        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("voucherCode", "ESHOP9999AAA8888");

        Payment newPayment = new Payment(
                payment.getId(),
                "VOUCHER",
                newPaymentData
        );

        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals("VOUCHER", findResult.getMethod());
    }

    @Test
    void testFindByIdIfIdFound() {

        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());

        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {

        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("unknown-id");

        assertNull(findResult);
    }

    @Test
    void testFindAllPayments() {

        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.findAll();

        assertEquals(3, paymentList.size());
    }

    @Test
    void testFindAllPaymentsIfEmpty() {

        List<Payment> paymentList = paymentRepository.findAll();

        assertTrue(paymentList.isEmpty());
    }
}