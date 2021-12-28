/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.PropertyDao;
import com.bern.norstienpaymentsapi.entity.Address;
import com.bern.norstienpaymentsapi.entity.Property;
import java.time.LocalDateTime;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
/*@Transactional*/
public class CamelDataReader {

    @Autowired
    PropertyDao propertyManager;
    
    private static final Logger LOG = LoggerFactory.getLogger(CamelDataReader.class);
    
    public void print(Exchange exchange) {
        
        System.out.println("Printing");
        
        System.out.println(exchange.getIn().getBody());
    }
    
    public void hello(){
        System.out.println("Hello at " + LocalDateTime.now());
    }
    
    public void process(String  line){
        
        
        String[] fields = line.split(",");
        
        Property newProperty = new Property();
        Address newAddress = new Address();
        
        /*
        * CSV Header
        *addressline1,addressline2,city,statecode,zip,propertyName,propertyImageUrl
        *
        */
        
        newAddress.setAddressline1(fields[0]);
        newAddress.setAddressline2(fields[1]);
        newAddress.setCity(fields[2]);
        newAddress.setStatecode(fields[3]);
        newAddress.setZip(fields[4]);
        
        newProperty.setName(fields[5]);
        newProperty.setImageUrl(fields[6]);
        
        newProperty.setAddress(newAddress);
        
        propertyManager.save(newProperty);
    }
}
