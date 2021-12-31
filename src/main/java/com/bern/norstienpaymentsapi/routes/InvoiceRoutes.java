/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.InvoiceDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class InvoiceRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:getAllInvoices").routeId("allInvoices").bean(InvoiceDao.class, "findAll");

        from("direct:getInvoicesByLease").routeId("getInvByLea").bean(InvoiceDao.class, "findInvoicesByLease");

        from("direct:postInvoice").routeId("saveInvoice").bean(InvoiceDao.class, "createInvoice");

        from("direct:getInvoiceById").routeId("getInvById").bean(InvoiceDao.class, "findById");

        from("direct:deleteInvoiceById").routeId("deleteInvById").bean(InvoiceDao.class, "deleteById");

        from("direct:deleteInvoiceByUuid").routeId("deleteInvaByUuid").bean(InvoiceDao.class, "deleteByUuid");

        from("direct:updateInvoice").routeId("updateInv").bean(InvoiceDao.class, "updateInvoice");
    }

}
