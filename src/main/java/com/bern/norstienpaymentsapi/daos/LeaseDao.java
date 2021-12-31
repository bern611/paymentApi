/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Lease;
import com.bern.norstienpaymentsapi.entity.Tenant;
import com.bern.norstienpaymentsapi.repositories.LeaseRepository;
import java.time.ZonedDateTime;
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
public class LeaseDao {

    @Autowired
    private LeaseRepository leaseRepo;

    @Autowired
    private TenantDao tenantManager;

    public Lease createLease(@Header("uuid") UUID tenantUuid, @Body @Valid Lease lease) {

        Tenant tenant = tenantManager.findByUuid(tenantUuid);
        lease.setTenant(tenant);
        lease.setCreatedAt(ZonedDateTime.now());
        lease.setUpdatedAt(ZonedDateTime.now());

        return save(lease);
    }

    private Lease save(Lease lease) {
        try {
            return leaseRepo.save(lease);
        } catch (Exception e) {
            throw new RuntimeException("Failed To Save Tenant");
        }
    }

    public Lease findById(@Body long id) {
        try {
            return leaseRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Lease With Id " + id);
        }
    }

    public List<Lease> findLeasesByTenant(@Body UUID tenantUuid) {
        Tenant tenant = tenantManager.findByUuid(tenantUuid);

        return leaseRepo.findLeasesByTenant(tenant);
    }

    public List<Lease> findAll() {
        return leaseRepo.findAll();
    }

    public void deleteById(@Body long id) {
        try {
            leaseRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Lease With Id " + id);
        }
    }

    public void deleteByUuid(@Body UUID uuid) {
        try {
            leaseRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Lease With Uuid " + uuid);
        }
    }

    public Lease updateLease(@Header("id") long id, @Body JsonObject payload) {
        if (payload != null && (payload.containsKey("leaseStatus") || payload.containsKey("isLeaseAutoRenew"))) {

            Lease lease = findById(id);
            if (lease == null) {
                throw new EntityNotFoundException("Unable to locate Lease with ID " + id);
            } 
            else {
                if (payload.containsKey("leaseStatus")) {
                    lease.setStatus(payload.getString("leaseStatus"));
                }

                if (payload.containsKey("isLeaseAutoRenew")) {
                    lease.setIsAutoRenew(payload.getBoolean("isLeaseAutoRenew"));
                }

                lease.setUpdatedAt(ZonedDateTime.now());
                return save(lease);
            }
        } 
        
        else {
            throw new InvalidPayloadException("Payload Must Contain Keys leaseStatus and/or isLeaseAutoRenew");
        }

    }

    public Lease findByUuid(@Body UUID uuid) {
        try {
            return leaseRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Lease With Uuid " + uuid);
        }
    }

}
