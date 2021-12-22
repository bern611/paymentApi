/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.Lease;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author unknown
 */
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAll();

    List<Invoice> findInvoicesByLease(Lease lease);

    Invoice findById(long id);

    Invoice findByUuid(UUID uuid);

    public void deleteByUuid(UUID uuid);
}
