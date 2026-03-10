package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setId("product-1");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(2);

        products.add(product);

        order = new Order(
                "order-1",
                products,
                1708570000L,
                "Safira Sudrajat"
        );

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testAddPayment() {

        Payment payment = new Payment("payment-1", "VOUCHER", "PENDING", paymentData);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("VOUCHER", result.getMethod());

        assertEquals(paymentData, result.getPaymentData());
    }

    @Test
    void testSetStatusSuccessAlsoUpdateOrderStatus() {

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejectedAlsoUpdateOrderFailed() {

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentIfFound() {

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {

        doReturn(null).when(paymentRepository).findById("unknown-id");

        Payment result = paymentService.getPayment("unknown-id");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {

        List<Payment> payments = new ArrayList<>();

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        payments.add(payment);

        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(1, result.size());

        verify(paymentRepository, times(1)).findAll();
    }
}