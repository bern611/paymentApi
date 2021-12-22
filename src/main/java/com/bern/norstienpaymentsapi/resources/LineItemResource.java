/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.daos.LineItemDao;
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
    LineItemDao lineItemManager;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response createLineItem(@PathParam("uuid") UUID invoiceUuid, LineItem lineItem) {

        return Response.ok().entity(lineItemManager.createLineItem(invoiceUuid, lineItem)).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateLineItem(@PathParam("id") long id, JsonObject payload) {

        return Response.ok(lineItemManager.updateLineItem(id, payload)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/invoices/uuid/{uuid}")
    public Response findLineItemByInvoice(@PathParam("uuid") UUID invoiceUuid) {

        List<LineItem> lineItems = lineItemManager.findLineItemsByInvoice(invoiceUuid);
        if (!lineItems.isEmpty()) {
            return Response.ok(lineItems).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    public List<LineItem> getAllLineItems() {
        return lineItemManager.findAll();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        return Response.ok(lineItemManager.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLineItem(@PathParam("id") long id) {
        lineItemManager.delete(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteLineItem(@PathParam("uuid") UUID uuid) {
        lineItemManager.deleteByUuid(uuid);
        return Response.noContent().build();
    }
}
