package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Payment {

    private static final String SUCCESS = "SUCCESS";
    private static final String REJECTED = "REJECTED";

    String id;
    String method;

    @Setter
    String status;

    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        validatePayment();
    }

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this(id, method, paymentData);
        this.status = status;
    }

    private void validatePayment() {
        switch (method) {
            case "VOUCHER":
                validateVoucher();
                break;
            case "COD":
                validateCOD();
                break;
            case "BANK_TRANSFER":
                validateBankTransfer();
                break;
            default:
                status = REJECTED;
        }
    }

    private void validateVoucher() {
        String voucher = paymentData.get("voucherCode");

        if (voucher == null || voucher.length() != 16 || !voucher.startsWith("ESHOP")) {
            status = REJECTED;
            return;
        }

        long digitCount = voucher.chars()
                .filter(Character::isDigit)
                .count();

        status = (digitCount == 8) ? SUCCESS : REJECTED;
    }

    private void validateCOD() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (isEmpty(address) || isEmpty(deliveryFee)) {
            status = REJECTED;
        } else {
            status = SUCCESS;
        }
    }

    private void validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (isEmpty(bankName) || isEmpty(referenceCode)) {
            status = REJECTED;
        } else {
            status = SUCCESS;
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
}