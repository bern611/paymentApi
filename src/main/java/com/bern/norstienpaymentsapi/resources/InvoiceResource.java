/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.daos.InvoiceDao;
import com.bern.norstienpaymentsapi.daos.LeaseDao;
import com.bern.norstienpaymentsapi.entity.Invoice;
import com.bern.norstienpaymentsapi.entity.Lease;
import com.bern.norstienpaymentsapi.repositories.InvoiceRepository;
import com.bern.norstienpaymentsapi.repositories.LeaseRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
@Path("/invoices")
public class InvoiceResource {

    @Autowired
    InvoiceDao invoiceManager;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/leases/uuid/{uuid}")
    public Response createInvoice(@PathParam("uuid") UUID leaseUuid, Invoice invoice) {
        return Response.ok(invoiceManager.createInvoice(leaseUuid, invoice)).build();

    }

    @GET
    @Produces("application/json")
    @Path("/leases/uuid/{uuid}")
    public Response findInvoiceByLease(@PathParam("uuid") UUID leaseUuid) {

        List<Invoice> invoices = invoiceManager.findInvoicesByLease(leaseUuid);
        if (!invoices.isEmpty()) {
            return Response.ok(invoices).build();
        } else {
            return Response.noContent().build();
        }

    }

    @GET
    @Produces("application/json")
    public List<Invoice> findAll() {
        return invoiceManager.findAll();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getInvoiceById(@PathParam("id") long id) {
        return Response.ok().entity(invoiceManager.findById(id)).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateInvoice(@PathParam("id") long id, JsonObject payload) {
        
        

        

        Invoice invoice = invoiceManager.findById(id);
        if (invoice == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not find invoice with id " + id).build();
        } else {
            

            return Response.ok(invoiceManager.updateInvoice(id, payload)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteInvoice(@PathParam("id") long id) {
        invoiceManager.delete(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteProperty(@PathParam("uuid") UUID uuid) {
        invoiceManager.deleteByUuid(uuid);
        return Response.noContent().build();
    }

}
