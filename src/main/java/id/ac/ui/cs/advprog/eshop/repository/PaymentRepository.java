package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

@Repository
public class PaymentRepository {

    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int ii = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.remove(ii);
                paymentData.add(ii, payment);
                return payment;
            }
            ii += 1;
        }

        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return paymentData;
    }
}