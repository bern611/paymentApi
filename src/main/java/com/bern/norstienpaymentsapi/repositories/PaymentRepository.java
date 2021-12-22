/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.Payment;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author unknown
 */
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAll();

    List<Payment> findPaymentsByInvoice(Invoice invoice);
    
    Payment findById(long id);

    Payment findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);


 
}
