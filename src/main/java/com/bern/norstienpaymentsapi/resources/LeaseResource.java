/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.entity.Lease;
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
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author unknown
 */
@Transactional
@Path("/leases")
public class LeaseResource {

    @Autowired
    private FluentProducerTemplate template;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/tenants/uuid/{uuid}")
    public Response createLease(@PathParam("uuid") UUID uuid, @Valid Lease lease) {

        return Response.ok(template
                .withDefaultEndpoint("direct:postLease")
                .withHeader("uuid", uuid)
                .withBody(lease)
                .request())
                .build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateLease(@PathParam("id") long id, JsonObject payload) {

        return Response.ok(
                template.withDefaultEndpoint("direct:updateLease")
                        .withHeader("id", id)
                        .withBody(payload)
                        .request())
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/tenants/uuid/{uuid}")
    public Response findLeasesByTenant(@PathParam("uuid") UUID uuid) {

        List<Lease> leases = (List<Lease>) template
                .withDefaultEndpoint("direct:getLeasesByTenant")
                .withBody(uuid)
                .request();
        
        if (!leases.isEmpty()) {
            return Response.ok(leases).build();
        } else {
            return Response.noContent().build();
        }

    }

    @GET
    @Produces("application/json")
    public Response findAll() {
        
        List<Lease> leases = (List<Lease>) template.withDefaultEndpoint("direct:getAllLeases").request();
        
        if (leases.size() > 0) {
            return Response.ok(leases).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getLeaseById(@PathParam("id") long id) {
        return Response.ok().entity(
                template.withDefaultEndpoint("direct:getLeaseById")
                        .withBody(id).request())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLease(@PathParam("id") long id) {
        template.withDefaultEndpoint("direct:deleteLeaseById").withBody(id).request();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteLease(@PathParam("uuid") UUID uuid) {
        template.withDefaultEndpoint("direct:deleteLeaseByUuid").withBody(uuid).request();
        return Response.noContent().build();
    }
}
