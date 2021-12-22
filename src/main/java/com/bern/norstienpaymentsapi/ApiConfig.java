/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi;

import com.bern.norstienpaymentsapi.resources.ExceptionHandler;
import com.bern.norstienpaymentsapi.resources.InvoiceResource;
import com.bern.norstienpaymentsapi.resources.LeaseResource;
import com.bern.norstienpaymentsapi.resources.LineItemResource;
import com.bern.norstienpaymentsapi.resources.PaymentResource;
import com.bern.norstienpaymentsapi.resources.PropertyResource;
import com.bern.norstienpaymentsapi.resources.TenantResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component

public class ApiConfig extends ResourceConfig {
    
    public ApiConfig(){
        register(PropertyResource.class);
        register(TenantResource.class);
        register(InvoiceResource.class);
        register(PaymentResource.class);
        register(LineItemResource.class);
        register(LeaseResource.class);
        register(ExceptionHandler.class);
        //packages("com.bern.norstienpaymentsapi.resources");
    }
}
