/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.TenantDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class TenantRoutes extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        from("direct:getAllTenants").routeId("allTenants").bean(TenantDao.class,"findAll");
        
        from("direct:getTenantsByProperty").routeId("getTenByProp").bean(TenantDao.class,"findTenantsByProperty");
        
        from("direct:postTenant").routeId("saveTenant").bean(TenantDao.class,"createTenant");
        
        from("direct:getTenantById").routeId("getTenById").bean(TenantDao.class,"findById");
        
        from("direct:deleteTenantById").routeId("deleteTenById").bean(TenantDao.class,"deleteById");
        
        from("direct:deleteTenantByUuid").routeId("deleteTenByUuid").bean(TenantDao.class,"deleteByUuid");
        
        from("direct:updateTenant").routeId("updateTen").bean(TenantDao.class,"updateTenantResidence");
    }
    
}
