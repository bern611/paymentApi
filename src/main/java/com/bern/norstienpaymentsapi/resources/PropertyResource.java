/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

/**
 *
 * @author unknown
 */
@Path("/property")
public class PropertyResource {

    @AutoWired
    PropertyRepository manager;

    @POST
    @Consumes("application/json")
    public Response createProperty(Property property) {

        return Response.ok().entity(manager.save(property)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getPropertyById(@PathParam("id") long id)
            return Response.ok().entity(SecurityManager.findById(id));
}
