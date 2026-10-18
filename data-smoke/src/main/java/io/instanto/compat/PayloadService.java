package io.instanto.compat;

import jakarta.ws.rs.*;
import org.dominokit.rest.shared.request.service.annotations.RequestFactory;

@RequestFactory
@Path("contracts")
@Produces("application/json")
@Consumes("application/json")
public interface PayloadService {
  @GET
  @Path("payload/{id}")
  Payload get(@PathParam("id") String id, @QueryParam("search") String search);

  @POST
  @Path("payload")
  Payload post(Payload payload);

  @GET
  @Path("missing")
  Payload missing();
}
