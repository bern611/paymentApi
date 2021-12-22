/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.daos.LeaseDao;
import com.bern.norstienpaymentsapi.daos.TenantDao;
import com.bern.norstienpaymentsapi.entity.Lease;
import com.bern.norstienpaymentsapi.entity.Tenant;
import java.time.ZonedDateTime;
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
@Path("/leases")
public class LeaseResource {
    
    @Autowired
    LeaseDao leaseManager;
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/tenants/uuid/{uuid}")
    public Response createLease(@PathParam("uuid") UUID uuid, @Valid Lease lease) {
        return Response.ok(leaseManager.createLease(uuid, lease)).build();
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateLease(@PathParam("id") long id, JsonObject payload) {
        
        return Response.ok(leaseManager.updateLease(id, payload)).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/tenants/uuid/{uuid}")
    public Response findLeasesByTenant(@PathParam("uuid") UUID uuid) {
        
        List<Lease> leases = leaseManager.findLeasesByTenant(uuid);
        if (!leases.isEmpty()) {
            return Response.ok(leases).build();
        } else {
            return Response.noContent().build();
        }
        
    }
    
    @GET
    @Produces("application/json")
    public List<Lease> findAll() {
        return leaseManager.findAll();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getLeaseById(@PathParam("id") long id) {
        return Response.ok().entity(leaseManager.findById(id)).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteLease(@PathParam("id") long id) {
        leaseManager.delete(id);
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteLease(@PathParam("uuid") UUID uuid) {
        leaseManager.deleteByUuid(uuid);
        return Response.noContent().build();
    }
}
