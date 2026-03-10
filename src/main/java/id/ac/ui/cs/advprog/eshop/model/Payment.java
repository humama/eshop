package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Payment {

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
        if (method.equals("VOUCHER")) {
            validateVoucher();
        } else if (method.equals("COD")) {
            validateCOD();
        } else if (method.equals("BANK_TRANSFER")) {
            validateBankTransfer();
        } else {
            this.status = "REJECTED";
        }
    }

    private void validateVoucher() {
        String voucher = paymentData.get("voucherCode");

        if (voucher == null || voucher.length() != 16 || !voucher.startsWith("ESHOP")) {
            this.status = "REJECTED";
            return;
        }

        int digitCount = 0;
        for (char c : voucher.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }

        if (digitCount == 8) {
            this.status = "SUCCESS";
        } else {
            this.status = "REJECTED";
        }
    }

    private void validateCOD() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty() ||
            deliveryFee == null || deliveryFee.isEmpty()) {
            this.status = "REJECTED";
        } else {
            this.status = "SUCCESS";
        }
    }

    private void validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() ||
            referenceCode == null || referenceCode.isEmpty()) {
            this.status = "REJECTED";
        } else {
            this.status = "SUCCESS";
        }
    }
}