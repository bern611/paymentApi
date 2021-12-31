/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.resources;

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
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Transactional
@Component("propertyApi")
@Path("/properties")
public class PropertyResource {

    @Autowired
    private FluentProducerTemplate template;

    @GET
    @Produces("application/json")
    public Response findAll() {
        List<Property> properties = (List<Property>) template.withDefaultEndpoint("direct:getAllProperties").request();
        if (properties.size() > 0) {
            return Response.ok(properties).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")

    public Response createProperty(Property property) {

        return Response.ok().entity(
                template.withDefaultEndpoint("direct:postProperty")
                        .withBody(property)
                        .request())
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getPropertyById(@PathParam("id") long id) {

        //return Response.ok().entity(propertyManager.findById(id)).build();
        return Response.ok().entity(
                template.withDefaultEndpoint("direct:getPropertyById")
                        .withBody(id).request())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProperty(@PathParam("id") long id) {

        //propertyManager.deleteById(id);
        template.withDefaultEndpoint("direct:deletePropertyById").withBody(id).request();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/uuid/{uuid}")
    public Response deleteProperty(@PathParam("uuid") UUID uuid) {
        //propertyManager.deleteByUuid(uuid);
        template.withDefaultEndpoint("direct:deletePropertyByUuid").withBody(uuid).request();
        return Response.noContent().build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response updateProperty(@PathParam("id") long id, JsonObject payload) {

        //return Response.ok(propertyManager.updateProperty(id, payload)).build();
        return Response.ok(
                template.withDefaultEndpoint("direct:updateProperty")
                        .withHeader("id", id)
                        .withBody(payload)
                        .request())
                .build();
    }
}
