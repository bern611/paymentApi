/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.PropertyDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author unknown
 */

//@Component
public class PropertyRoute extends RouteBuilder{
    
    @Autowired
    PropertyDao propertyManager;
    
    @Override
    public void configure() throws Exception {
        from("direct:propertyApi").routeId("propertyRoute").to("bean:propertyManager", "save(property)");
    }
    
}
