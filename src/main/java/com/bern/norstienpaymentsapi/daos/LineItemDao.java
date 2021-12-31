/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.LineItem;
import com.bern.norstienpaymentsapi.repositories.LineItemRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author unknown
 */
@Service
@Transactional
public class LineItemDao {

    @Autowired
    LineItemRepository lineItemRepo;

    @Autowired
    InvoiceDao invoiceManager;

    public LineItem createLineItem(@Header("uuid") UUID invoiceUuid, @Body @Valid LineItem lineItem) {
        Invoice invoice = invoiceManager.findByUuid(invoiceUuid);

        lineItem.setInvoice(invoice);

        return save(lineItem);
    }

    private LineItem save(LineItem lineItem) {
        return lineItemRepo.save(lineItem);
    }

    public Object updateLineItem(@Header("id") long id, @Body JsonObject payload) {

        if (payload != null && (payload.containsKey("lineItemQuantity") || payload.containsKey("lineItemDescription") || payload.containsKey("lineItemAmount"))) {

            LineItem lineItem = findById(id);
            if (lineItem != null) {
                if (payload.containsKey("lineItemQuantity")) {
                    lineItem.setQuantity(payload.getInteger("lineItemQuantity"));
                }

                if (payload.containsKey("lineItemDescription")) {
                    lineItem.setDescription(payload.getString("lineItemDescription"));
                }

                if (payload.containsKey("lineItemAmount")) {
                    lineItem.setAmount(payload.getDouble("lineItemAmount"));
                }
                return save(lineItem);
            } else{
                throw new EntityNotFoundException("Could not find LineItem With Id " + id);
            }
        } else {
            throw new InvalidPayloadException("Payload must contain keys lineItemQuantity, lineItemDescription and/or lineItemAmount ");
        }
    }

    public LineItem findById(@Body long id) {
        try {
            return lineItemRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find LineItem With Id " + id);
        }
    }

    public LineItem findByUuid(@Body UUID uuid) {
        try {
            return lineItemRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find LineItem With Uuid " + uuid);
        }
    }

    public List<LineItem> findLineItemsByInvoice(@Body UUID invoiceUuid) {
        Invoice invoice = invoiceManager.findByUuid(invoiceUuid);
        return lineItemRepo.findLineItemsByInvoice(invoice);
    }

    public List<LineItem> findAll() {
        return lineItemRepo.findAll();
    }

    public void deleteById(@Body long id) {
        try {
            lineItemRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find LineItem With Id " + id);
        }
    }

    public void deleteByUuid(@Body UUID uuid) {
        try {
            lineItemRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find LineItem With Uuid " + uuid);
        }
    }

}
