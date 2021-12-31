/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.LeaseDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class LeaseRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getAllLeases").routeId("allLeases").bean(LeaseDao.class,"findAll");
        
        from("direct:getLeasesByTenant").routeId("getLeaByTen").bean(LeaseDao.class,"findLeasesByTenant");
        
        from("direct:postLease").routeId("saveLease").bean(LeaseDao.class,"createLease");
        
        from("direct:getLeaseById").routeId("getLeaById").bean(LeaseDao.class,"findById");
        
        from("direct:deleteLeaseById").routeId("deleteLeaById").bean(LeaseDao.class,"deleteById");
        
        from("direct:deleteLeaseByUuid").routeId("deleteLeaByUuid").bean(LeaseDao.class,"deleteByUuid");
        
        from("direct:updateLease").routeId("updateLea").bean(LeaseDao.class,"updateLease");
    }
    
    
}
