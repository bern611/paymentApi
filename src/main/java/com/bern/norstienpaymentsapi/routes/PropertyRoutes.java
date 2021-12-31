/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.PropertyDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */

@Component
public class PropertyRoutes extends RouteBuilder{
    
 
    
    @Override
    public void configure() throws Exception {
        
        from("direct:getAllProperties").routeId("allProps").bean(PropertyDao.class,"findAll");
        
        from("direct:postProperty").routeId("propertyRoute")./*to("bean:propertyManager", "save(property)")*/bean(PropertyDao.class,"save");
        
        from("direct:getPropertyById").routeId("getPropById").bean(PropertyDao.class,"findById");
        
        from("direct:deletePropertyById").routeId("deletePropById").bean(PropertyDao.class,"deleteById");
        
        from("direct:deletePropertyByUuid").routeId("deletePropByUuid").bean(PropertyDao.class,"deleteByUuid");
        
        from("direct:updateProperty").routeId("updateProp").bean(PropertyDao.class,"updateProperty");
    }
    
}
