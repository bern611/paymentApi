/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.Lease;
import com.bern.norstienpaymentsapi.repositories.InvoiceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author unknown
 */
@Service
@Transactional
public class InvoiceDao {

    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    LeaseDao leaseManager;

    public Invoice createInvoice(UUID leaseUuid, @Valid Invoice invoice) {
        Lease lease = leaseManager.findByUuid(leaseUuid);

        invoice.setLease(lease);
        invoice.setInvoiceDate(LocalDate.now());
        return save(invoice);
    }

    private Invoice save(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    public List<Invoice> findInvoicesByLease(UUID leaseUuid) {
        Lease lease = leaseManager.findByUuid(leaseUuid);
        return invoiceRepo.findInvoicesByLease(lease);
    }

    public List<Invoice> findAll() {
        return invoiceRepo.findAll();
    }

    public Invoice findById(long id) {
        try {
            return invoiceRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Invoice With Id " + id);
        }
    }

    public Invoice findByUuid(UUID uuid) {
        try {
            return invoiceRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Invoice With Uuid " + uuid);
        }
    }

    public void delete(long id) {
        try {
            invoiceRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Invoice With Id " + id);
        }
    }

    public void deleteByUuid(UUID uuid) {
        try {
            invoiceRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Invoice With Uuid " + uuid);
        }
    }

    public Invoice updateInvoice(long id, JsonObject payload) {
        if (payload != null && (payload.containsKey("paymentDueDate") || payload.containsKey("paidDate"))) {
            
            Invoice invoice = findById(id);
            
            if (payload.containsKey("paymentDueDate")) {
                invoice.setPaymentDueDate(LocalDate.parse(payload.getString("paymentDueDate")));
            }

            if (payload.containsKey("paidDate")) {
                invoice.setPaidDate(LocalDate.parse(payload.getString("paidDate")));
            }
            
            return save(invoice);
        }
        else{
            throw new InvalidPayloadException ("payload must contain keys paymentDueDate and/or paidDate");
        }
    }

}
