/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.LineItemDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class LineItemRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:getAllLineItems").routeId("allLineItems").bean(LineItemDao.class, "findAll");

        from("direct:getLineItemsByInvoice").routeId("getLiByInv").bean(LineItemDao.class, "findLineItemsByInvoice");

        from("direct:postLineItem").routeId("saveLineItem").bean(LineItemDao.class, "createLineItem");

        from("direct:getLineItemById").routeId("getLiById").bean(LineItemDao.class, "findById");

        from("direct:deleteLineItemById").routeId("deleteLiById").bean(LineItemDao.class, "deleteById");

        from("direct:deleteLineItemByUuid").routeId("deleteLiByUuid").bean(LineItemDao.class, "deleteByUuid");

        from("direct:updateLineItem").routeId("updateLi").bean(LineItemDao.class, "updateLineItem");
    }
    
}
