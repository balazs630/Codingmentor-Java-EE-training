package hu.oktatas.java.ee.webshop.restservices;

import hu.oktatas.java.ee.webshop.beans.UserDTO;
import hu.oktatas.java.ee.webshop.db.UserDB;
import hu.oktatas.java.ee.webshop.db.exceptions.UsernameAlreadyTakenException;
import hu.oktatas.java.ee.webshop.db.exceptions.UsernameNotExistException;
import java.io.Serializable;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
@SessionScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserService implements Serializable {

    @Inject
    private UserDB userDB;

    @POST
    @Path("/add")
    // @AdminLoggedInCheck
    public UserDTO add(UserDTO user, @Context HttpServletRequest request) throws UsernameAlreadyTakenException {
        return userDB.registrate(user);
    }

    @DELETE
    @Path("/remove")
    // @AdminLoggedInCheck
    public boolean remove(UserDTO user, @Context HttpServletRequest request) {
        return userDB.removeUser(user);
    }

    @GET
    // @UserLoggedInCheck
    @Path("/id/{id}")
    public UserDTO getUserById(@PathParam("id") String id) throws UsernameNotExistException {
        return userDB.getUser(id);
    }

    @GET
    // @UserLoggedInCheck
    public Collection<UserDTO> getUsers() {
        return (Collection<UserDTO>) userDB.getUserDataBase();
    }

    @POST
    @Path("/login")
    public UserDTO login(UserDTO loginUser, @Context HttpServletRequest request) throws UsernameNotExistException {
        if (userDB.authenticate(loginUser.getUserName(), loginUser.getPassword())) {
            UserDTO user = userDB.getUser(loginUser.getUserName());
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.getSession();
                return user;

            } else {
                session.invalidate();
            }
        }
        throw new UsernameNotExistException("login failed: wrong username or password");
    }

}