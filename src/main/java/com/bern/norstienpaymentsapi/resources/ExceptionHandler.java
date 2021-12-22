/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi.resources;

import com.bern.norstienpaymentsapi.InvalidPayloadException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author unknown
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        if (e instanceof EntityNotFoundException || e instanceof InvalidPayloadException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } else {
            return Response.serverError().build();
        }
    }

}
