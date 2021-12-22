/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.daos.TenantDao;
import com.bern.norstienpaymentsapi.entity.Tenant;
import com.bern.norstienpaymentsapi.repositories.PropertyRepository;
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
@Path("/tenants")
public class TenantResource {

    @Autowired
    TenantDao tenantManager;

    @Autowired
    PropertyRepository propertyManager;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/properties/uuid/{uuid}")
    public Response createTenant(@PathParam("uuid") UUID uuid, @Valid Tenant tenant) {
        return Response.ok(tenantManager.createTenant(uuid, tenant)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/properties/uuid/{uuid}")
    public Response findTenantsByProperty(@PathParam("uuid") UUID propertyUuid) {

        List<Tenant> tenants = tenantManager.findTenantsByProperty(propertyUuid);
        if (tenants.size() > 0) {
            return Response.ok(tenants).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    public Response getAllTenants() {
        List<Tenant> tenants = tenantManager.findAll();
        if (tenants.size() > 0) {
            return Response.ok(tenants).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getTenantById(@PathParam("id") long id) {
        return Response.ok().entity(tenantManager.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTenant(@PathParam("id") long id) {
        tenantManager.deleteById(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteProperty(@PathParam("uuid") UUID uuid) {
        tenantManager.deleteByUuid(uuid);
        return Response.noContent().build();
    }

    @PUT
    @Produces("application/json")
    @Path("/{id}")
    public Response updateTenantResidence(@PathParam("id") long id, JsonObject payload) {
        return Response.ok(tenantManager.updateTenantResidence(id, payload)).build();
    }

}
