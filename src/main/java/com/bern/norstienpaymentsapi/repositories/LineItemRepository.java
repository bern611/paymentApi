/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.repositories;

import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.LineItem;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author unknown
 */
@Transactional
public interface LineItemRepository extends JpaRepository<LineItem, Long>{
    
    List<LineItem> findAll();

    List<LineItem> findLineItemsByInvoice(Invoice invoice);
    
    LineItem findById(long id);

    LineItem findByUuid(UUID uuid);

    public void deleteByUuid(UUID uuid);
    
}
