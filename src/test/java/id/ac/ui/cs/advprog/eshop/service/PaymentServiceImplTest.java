package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(
                "payment-1",
                "VOUCHER",
                paymentData
        );
    }

    @Test
    void testCreatePayment() {
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.createPayment(payment);

        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testCreatePaymentIfAlreadyExist() {
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.createPayment(payment);

        assertNull(result);
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    void testUpdateStatus() {
        Payment updatedPayment = new Payment(
                payment.getId(),
                "VOUCHER",
                payment.getPaymentData()
        );

        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(updatedPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updateStatus(payment.getId(), "SUCCESS");

        assertEquals(payment.getId(), result.getId());
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.updateStatus(payment.getId(), "MEOW"));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).findById("unknown-id");

        assertThrows(NoSuchElementException.class,
                () -> paymentService.updateStatus("unknown-id", "SUCCESS"));
    }

    @Test
    void testFindByIdIfFound() {
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfNotFound() {
        doReturn(null).when(paymentRepository).findById("unknown-id");

        Payment result = paymentService.findById("unknown-id");

        assertNull(result);
    }
}