/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Property;
import com.bern.norstienpaymentsapi.entity.Tenant;
import com.bern.norstienpaymentsapi.repositories.TenantRepository;
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
public class TenantDao {

    @Autowired
    private TenantRepository tenantRepo;

    @Autowired
    private PropertyDao propertyManager;

    public Tenant createTenant(@Header("uuid") UUID propertyUuid, @Body @Valid Tenant tenant) {

        Property property = propertyManager.findByUuid(propertyUuid);

        tenant.setProperty(property);
        tenant.setCreatedAt(ZonedDateTime.now());
        tenant.setUpdatedAt(ZonedDateTime.now());

        return save(tenant);

    }

    private Tenant save(Tenant tenant) {
        try {
            return tenantRepo.save(tenant);
        } catch (Exception ex) {
            throw new RuntimeException("Error Saving Tenant", ex);
        }
    }

    public List<Tenant> findTenantsByProperty(@Body UUID propertyUuid) {

        Property property = propertyManager.findByUuid(propertyUuid);
        return tenantRepo.findTenantsByProperty(property);

    }

    public List<Tenant> findAll() {
        return tenantRepo.findAll();
    }

    public Tenant findById(@Body long id) {
        try {
            return tenantRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Tenant With Id " + id);
        }
    }

    public void deleteById(@Body long id) {
        try {
            tenantRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Tenant With Id " + id);
        }
    }

    public void deleteByUuid(@Body UUID uuid) {
        try {
            tenantRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Tenant With UUID " + uuid);
        }
    }

    public Tenant updateTenantResidence(@Header("id") long id, @Body JsonObject payload) {
        if (payload != null && payload.containsKey("newPropertyUuid")) {

            UUID propertyUuid = UUID.fromString(payload.getString("newPropertyUuid"));

            Property updateProperty = propertyManager.findByUuid(propertyUuid);
            Tenant updateTenant = findById(id);
            
            if(updateTenant!= null && updateProperty != null){
            
            updateTenant.setProperty(updateProperty);
            updateTenant.setUpdatedAt(ZonedDateTime.now());
            return updateTenant;
            }
            else{
                throw new EntityNotFoundException("Unable to locate pair Tenant # " + id + " and/or Property " + propertyUuid);
            }
        } else {
            throw new InvalidPayloadException("Payload Must Contain Key newPropertyUuid");
        }
    }

    public Tenant findByUuid(@Body UUID uuid) {
        try {
            return tenantRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Tenant With uuid " + uuid);
        }
    }

}
