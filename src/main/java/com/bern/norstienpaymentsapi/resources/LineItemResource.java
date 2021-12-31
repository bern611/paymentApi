/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.entity.LineItem;
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
@Path("/line_item")
public class LineItemResource {

    @Autowired
    private FluentProducerTemplate template;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response createLineItem(@PathParam("uuid") UUID invoiceUuid, LineItem lineItem) {
        
        return Response.ok(template
                .withDefaultEndpoint("direct:postLineItem")
                .withHeader("uuid", invoiceUuid)
                .withBody(lineItem)
                .request())
                .build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateLineItem(@PathParam("id") long id, JsonObject payload) {
        
        return Response.ok(
                template.withDefaultEndpoint("direct:updateLineItem")
                        .withHeader("id", id)
                        .withBody(payload)
                        .request())
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response findLineItemByInvoice(@PathParam("uuid") UUID invoiceUuid) {

        List<LineItem> lineItems = (List<LineItem>) template
                .withDefaultEndpoint("direct:getLineItemsByInvoice")
                .withBody(invoiceUuid)
                .request();
        
        if (!lineItems.isEmpty()) {
            return Response.ok(lineItems).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    public Response getAllLineItems() {
        List<LineItem> lineItems = (List<LineItem>) template.withDefaultEndpoint("direct:getAllLineItems").request();
        
        if (!lineItems.isEmpty()) {
            return Response.ok(lineItems).build();
        } else {
            return Response.noContent().build();
        }
        
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok().entity(
                template.withDefaultEndpoint("direct:getLineItemById")
                        .withBody(id).request())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLineItem(@PathParam("id") long id) {
        template.withDefaultEndpoint("direct:deleteLineItemById").withBody(id).request();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteLineItem(@PathParam("uuid") UUID uuid) {
        template.withDefaultEndpoint("direct:deleteLineItemByUuid").withBody(uuid).request();
        return Response.noContent().build();
    }
}
