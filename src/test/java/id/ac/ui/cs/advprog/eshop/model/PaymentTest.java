package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithValidVoucherShouldSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                paymentData
        );

        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentWithInvalidVoucherShouldRejected() {
        paymentData.put("voucherCode", "INVALIDCODE");

        Payment payment = new Payment(
                "payment-2",
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateCODWithValidDataShouldSuccess() {
        paymentData.put("address", "Jl. Margonda Raya");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(
                "payment-3",
                "COD",
                paymentData
        );

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("Jl. Margonda Raya", payment.getPaymentData().get("address"));
        assertEquals("10000", payment.getPaymentData().get("deliveryFee"));
    }

    @Test
    void testCreateCODWithEmptyAddressShouldRejected() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(
                "payment-4",
                "COD",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateCODWithEmptyDeliveryFeeShouldRejected() {
        paymentData.put("address", "Jl. Margonda Raya");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment(
                "payment-5",
                "COD",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferWithValidDataShouldSuccess() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "INV123456");

        Payment payment = new Payment(
                "payment-6",
                "BANK_TRANSFER",
                paymentData
        );

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("INV123456", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testCreateBankTransferWithEmptyBankNameShouldRejected() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "INV123456");

        Payment payment = new Payment(
                "payment-7",
                "BANK_TRANSFER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferWithEmptyReferenceCodeShouldRejected() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment(
                "payment-8",
                "BANK_TRANSFER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }
}