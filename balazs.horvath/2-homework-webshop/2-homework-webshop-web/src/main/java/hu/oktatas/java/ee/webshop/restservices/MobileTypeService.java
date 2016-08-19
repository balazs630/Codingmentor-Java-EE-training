package hu.oktatas.java.ee.webshop.restservices;

import hu.oktatas.java.ee.webshop.db.exceptions.MobileNotExistException;
import hu.oktatas.java.ee.webshop.beans.MobileType;
import hu.oktatas.java.ee.webshop.db.MobileDB;
import hu.oktatas.java.ee.webshop.util.VerifyLogin;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ejb.EJB;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/mobiletypes")
@SessionScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MobileTypeService implements Serializable {

    @EJB
    private transient MobileDB mobileDB;

    @POST
    public MobileType add(MobileType type, @Context HttpServletRequest request) {
        VerifyLogin.adminLogin(request);
        return mobileDB.addNewMobileType(type);
    }

    @DELETE
    @Path("/remove/{type}")
    public String remove(@PathParam("type") String type, @Context HttpServletRequest request) throws MobileNotExistException {
        VerifyLogin.adminLogin(request); 
        if (mobileDB.remove(type)) {
            return type;
        }
        throw new MobileNotExistException("remove failed: mobile not exist");
    }

    @GET
    public Collection<MobileType> getMobileTypes() {
        return mobileDB.getReservedMobileDB().keySet();
    }
}
