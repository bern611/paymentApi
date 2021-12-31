/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.entity.Invoice;
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
@Path("/invoices")
public class InvoiceResource {

    @Autowired
    private FluentProducerTemplate template;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/leases/uuid/{uuid}")
    public Response createInvoice(@PathParam("uuid") UUID leaseUuid, Invoice invoice) {

        return Response.ok(template
                .withDefaultEndpoint("direct:postInvoice")
                .withHeader("uuid", leaseUuid)
                .withBody(invoice)
                .request())
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/leases/uuid/{uuid}")
    public Response findInvoiceByLease(@PathParam("uuid") UUID leaseUuid) {

        List<Invoice> invoices = (List<Invoice>) template
                .withDefaultEndpoint("direct:getInvoicesByLease")
                .withBody(leaseUuid)
                .request();
        if (!invoices.isEmpty()) {
            return Response.ok(invoices).build();
        } else {
            return Response.noContent().build();
        }

    }

    @GET
    @Produces("application/json")
    public Response findAll() {
        List<Invoice> invoices = (List<Invoice>) template.withDefaultEndpoint("direct:getAllInvoices").request();

        if (invoices.size() > 0) {
            return Response.ok(invoices).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getInvoiceById(@PathParam("id") long id) {

        return Response.ok().entity(
                template.withDefaultEndpoint("direct:getInvoiceById")
                        .withBody(id).request())
                .build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateInvoice(@PathParam("id") long id, JsonObject payload) {

        return Response.ok(
                template.withDefaultEndpoint("direct:updateInvoice")
                        .withHeader("id", id)
                        .withBody(payload)
                        .request())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteInvoice(@PathParam("id") long id) {
        template.withDefaultEndpoint("direct:deleteInvoiceById").withBody(id).request();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteProperty(@PathParam("uuid") UUID uuid) {
        template.withDefaultEndpoint("direct:deleteInvoiceByUuid").withBody(uuid).request();
        return Response.noContent().build();
    }

}
