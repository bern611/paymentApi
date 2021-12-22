/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.Payment;
import com.bern.norstienpaymentsapi.entity.PaymentStatus;
import com.bern.norstienpaymentsapi.repositories.PaymentRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author unknown
 */
@Service
@Transactional
public class PaymentDao {

    @Autowired
    PaymentRepository paymentRepo;

    @Autowired
    InvoiceDao invoiceManager;

    public Payment createPayment(UUID invoiceUuid, Payment payment) {
        Invoice invoice = invoiceManager.findByUuid(invoiceUuid);

        payment.setInvoice(invoice);
        payment.setTransactionDate(ZonedDateTime.now());
        payment.setUpdatedAt(ZonedDateTime.now());

        return save(payment);
    }

    private Payment save(Payment payment) {
        return paymentRepo.save(payment);
    }

    public Payment updatePayment(long id, JsonObject payload) {
        if (payload != null && (payload.containsKey("paymentStatus"))) {
            Payment payment = findById(id);

            payment.setStatus(PaymentStatus.valueOf(payload.getString("paymentStatus")));
            payment.setUpdatedAt(ZonedDateTime.now());

            return save(payment);
        } else {
            throw new InvalidPayloadException("Payload must contain key paymentStatus");
        }
    }

    public Payment findById(long id) {
        try {
            return paymentRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Payment With Id " + id);
        }
    }
    
    public Payment findByUuid(UUID uuid) {
        try {
            return paymentRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Payment With Uuid " + uuid);
        }
    }

    public List<Payment> findPaymentsByInvoice(UUID invoiceUuid) {
        Invoice invoice = invoiceManager.findByUuid(invoiceUuid);
        return paymentRepo.findPaymentsByInvoice(invoice);
    }

    public List<Payment> findAll() {
        return paymentRepo.findAll();
    }

    public void delete(long id) {
        try {
            paymentRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Payment With Id " + id);
        }
    }

    public void deleteByUuid(UUID uuid) {
        try {
            paymentRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Payment With Uuid " + uuid);
        }
    }

}