package hu.oktatas.java.ee.webshop.restservices;

import hu.oktatas.java.ee.webshop.beans.MobileType;
import hu.oktatas.java.ee.webshop.shoppingcart.ShoppingCart;
import hu.oktatas.java.ee.webshop.shoppingcart.exceptions.MobileNotExistInTheCartException;
import hu.oktatas.java.ee.webshop.util.VerifyLogin;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ejb.EJB;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/shoppingcart")
@SessionScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartService implements Serializable {

    @EJB
    private transient ShoppingCart cart;

    @POST
    @Path("/add")
    public MobileType add(MobileType type, @Context HttpServletRequest request) {
        VerifyLogin.userLogin(request);
        cart.addItem(type, 1);
        return type;
    }

    @DELETE
    @Path("/remove/{type}")
    public String remove(@PathParam("type") String type, @Context HttpServletRequest request)
            throws MobileNotExistInTheCartException {
        VerifyLogin.userLogin(request);
        cart.removeItem(type, 1);
        return type;
    }

    @POST
    @Path("/checkout")
    public String checkout(@Context HttpServletRequest request) {
        VerifyLogin.userLogin(request);
        request.getSession().invalidate();
        return cart.checkout();
    }
}
