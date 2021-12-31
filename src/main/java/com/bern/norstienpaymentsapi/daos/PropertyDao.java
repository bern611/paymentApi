/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.daos;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import com.bern.norstienpaymentsapi.entity.Property;
import com.bern.norstienpaymentsapi.repositories.PropertyRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author unknown
 */
@Service
@Component("propertyManager")
@Transactional
public class PropertyDao {

    @Autowired
    private PropertyRepository propertyRepo;


    public List<Property> findAll() {
        return propertyRepo.findAll();
    }

    public Property save( @Body @Valid Property property) {
        try {

            Property returnProperty = propertyRepo.save(property);

            return returnProperty;
        } catch (Exception ex) {
            throw new RuntimeException("Error Saving Property");
        }
    }

    public Property findById(@Body long id) {
        try {
            return propertyRepo.findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Property With Id " + id);
        }
    }

    public void deleteById(@Body long id) {
        try {
            propertyRepo.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Unabled to Find Property With Id " + id);
        }
    }

    public void deleteByUuid(@Body UUID uuid) {
        try {
            propertyRepo.deleteByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Unabled to Find Property With UUID " + uuid);
        }
    }

    public Property updateProperty(@Header("id") long id, @Body JsonObject payload) {
        if (payload == null || !(payload.containsKey("name") || payload.containsKey("imageUrl"))) {
            throw new InvalidPayloadException("Payload must contain keys name or imageUrl");
        }

        Property foundById = findById(id);
        if (foundById == null) {
            throw new EntityNotFoundException("Unable to locate property with ID " + id);
        } else {
            if (payload.containsKey("name")) {
                foundById.setName(payload.getString("name"));
            }
            if (payload.containsKey("imageUrl")) {
                foundById.setImageUrl(payload.getString("imageUrl"));
            }
            return save(foundById);
        }
    }

    public Property findByUuid(UUID uuid) {
        try {
            return propertyRepo.findByUuid(uuid);
        } catch (Exception e) {
            throw new EntityNotFoundException("Could not find Property With UUID " + uuid);
        }
    }

}
