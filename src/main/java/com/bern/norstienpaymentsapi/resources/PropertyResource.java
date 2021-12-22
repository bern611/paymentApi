/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.daos.PropertyDao;
import com.bern.norstienpaymentsapi.entity.Property;
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
@Path("/properties")
public class PropertyResource {

    @Autowired
    PropertyDao propertyManager;

    @GET
    @Produces("application/json")
    public List<Property> findAll() {
        return propertyManager.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")

    public Response createProperty(Property property) {

        return Response.ok().entity(propertyManager.save(property)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getPropertyById(@PathParam("id") long id) {
        return Response.ok().entity(propertyManager.findById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProperty(@PathParam("id") long id) {
        
        propertyManager.deleteById(id);
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteProperty(@PathParam("uuid") UUID uuid) {
        propertyManager.deleteByUuid(uuid);
        return Response.noContent().build();
    }
    

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateProperty(@PathParam("id") long id, JsonObject payload) {
        
        return Response.ok(propertyManager.updateProperty(id, payload)).build();
    }
}
