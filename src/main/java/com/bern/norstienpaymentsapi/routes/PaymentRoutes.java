/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import com.bern.norstienpaymentsapi.daos.PaymentDao;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class PaymentRoutes extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        from("direct:getAllPayments").routeId("allPayments").bean(PaymentDao.class, "findAll");

        from("direct:getPaymentsByInvoice").routeId("getPayByInv").bean(PaymentDao.class, "findPaymentsByInvoice");

        from("direct:postPayment").routeId("savePayment").bean(PaymentDao.class, "createPayment");

        from("direct:getPaymentById").routeId("getPayById").bean(PaymentDao.class, "findById");

        from("direct:deletePaymentById").routeId("deletePayById").bean(PaymentDao.class, "deleteById");

        from("direct:deletePaymentByUuid").routeId("deletePayaByUuid").bean(PaymentDao.class, "deleteByUuid");

        from("direct:updatePayment").routeId("updatePay").bean(PaymentDao.class, "updatePayment");
    }
    
}
