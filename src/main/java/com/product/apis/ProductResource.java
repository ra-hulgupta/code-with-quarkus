
/*import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import io.quarkus.hibernate.reactive.panache.common.*;
import com.product.model.Product;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    @WithSession
    public Uni<List<Product>> getAll() {
        return Product.listAll();
    }
}
*/
package com.product.apis;

import java.util.List;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.product.model.Product;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/products")
@ApplicationScoped
@WithSession
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<List<Product>> getAllProducts() {
		return Product.listAll();
	}

	@GET
	@Path("/{id}")
	public Uni<Response> getById(@PathParam("id") Long id) {
		return Product.findById(id).onItem().ifNotNull().transform(person -> Response.ok(person).build()).onItem()
				.ifNull().continueWith(Response.status(Response.Status.NOT_FOUND).build());
	}

	/*
	 * @POST public Uni<Response> create(Product product) { return product.persist()
	 * .onItem().transform(inserted -> Response.created(URI.create("/products/" +
	 * product.id)).build()); }
	 */

	@POST
	public Uni<Response> create(Product product) {
		return product.persist().onItem()
				.transform(inserted -> Response.created(URI.create("/products/" + product.id)).build());
	}

	@PUT
	@Path("/{id}")
	public Uni<Response> update(@PathParam("id") Long id, Product updatedProduct) {
		return Product.<Product>findById(id).onItem().ifNotNull().invoke(product -> {
			product.name = updatedProduct.name;
			product.description = updatedProduct.description;
			product.price = updatedProduct.price;
			product.quantity = updatedProduct.quantity;
		}).onItem().ifNotNull().transform(product -> Response.ok(product).build()).onItem().ifNull()
				.continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}

	@DELETE
	@Path("/{id}")
	public Uni<Response> delete(@PathParam("id") Long id) {
		return Product.deleteById(id).onItem().transform(
				deleted -> deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build());
	}
}
