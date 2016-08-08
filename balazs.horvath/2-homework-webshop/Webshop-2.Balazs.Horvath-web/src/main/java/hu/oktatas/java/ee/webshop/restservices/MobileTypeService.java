package hu.oktatas.java.ee.webshop.restservices;

import hu.oktatas.java.ee.webshop.db.exceptions.MobileNotExistException;
import hu.oktatas.java.ee.webshop.beans.MobileType;
import hu.oktatas.java.ee.webshop.db.MobileDB;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/mobiletype")
@SessionScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class MobileTypeService implements Serializable {

    @Inject
    private MobileDB mobileDB;

    @POST
    @Path("/add")
    // @AdminLoggedInCheck
    public MobileType add(MobileType type, @Context HttpServletRequest request) {
        return mobileDB.addNewMobileType(type);
    }

    @DELETE
    @Path("/remove")
    // @AdminLoggedInCheck
    public MobileType remove(MobileType type, @Context HttpServletRequest request) throws MobileNotExistException{
        if (mobileDB.remove(type)) {
            return type;
        }
        throw new MobileNotExistException("remove failed: mobile not exist");
    }

    @GET
    // @UserLoggedInCheck
    public Collection<MobileType> getMobileTypes() {
        return (Collection<MobileType>) mobileDB.getReservedMobileDB();
    }
}