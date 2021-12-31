/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.entity.Payment;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author unknown
 */
@Transactional
@Path("/payments")
public class PaymentResource {

    @Autowired
    private FluentProducerTemplate template;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response createPayment(@PathParam("uuid") UUID invoiceUuid, Payment payment) {

        return Response.ok(template
                .withDefaultEndpoint("direct:postPayment")
                .withHeader("uuid", invoiceUuid)
                .withBody(payment)
                .request())
                .build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updatePayment(@PathParam("id") long id, JsonObject payload) {

        return Response.ok(
                template.withDefaultEndpoint("direct:updatePayment")
                        .withHeader("id", id)
                        .withBody(payload)
                        .request())
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response findPaymentByInvoice(@PathParam("uuid") UUID invoiceUuid) {

        List<Payment> payments = (List<Payment>) template
                .withDefaultEndpoint("direct:getPaymentsByInvoice")
                .withBody(invoiceUuid)
                .request();
        if (!payments.isEmpty()) {
            return Response.ok(payments).build();
        } else {
            return Response.noContent().build();
        }

    }

    @GET
    @Produces("application/json")
    public Response getAllPayments() {
        List<Payment> payments = (List<Payment>) template.withDefaultEndpoint("direct:getAllPayments").request();
        
        if (!payments.isEmpty()) {
            return Response.ok(payments).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok().entity(
                template.withDefaultEndpoint("direct:getPaymentById")
                        .withBody(id).request())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") long id) {
        template.withDefaultEndpoint("direct:deletePaymentById").withBody(id).request();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deletePayment(@PathParam("uuid") UUID uuid) {
        template.withDefaultEndpoint("direct:deletePaymentByUuid").withBody(uuid).request();
        return Response.noContent().build();
    }

}
