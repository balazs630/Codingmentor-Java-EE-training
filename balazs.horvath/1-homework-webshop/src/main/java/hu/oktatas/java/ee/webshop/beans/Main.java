package hu.oktatas.java.ee.webshop.beans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oktatas.java.ee.webshop.db.MobileDB;
import hu.oktatas.java.ee.webshop.db.UserDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final UserDB USERDB = UserDB.INSTANCE;
    private static final MobileDB MOBILEDB = MobileDB.INSTANCE;

    private Main() {
    }

    public static void main(String[] args) throws IOException {

        List<UserDTO> users = MAPPER.readValue(Main.class.getClassLoader().getResource("json/users.json"),
                new TypeReference<List<UserDTO>>() {
        });

        users.stream().forEach((user) -> {
            USERDB.registrate(user);
        });

         List<MobileType> mobiles = MAPPER.readValue(Main.class.getClassLoader().getResource("json/mobiles.json"),
                new TypeReference<List<MobileType>>() {
        });

         mobiles.stream().forEach((mobile) -> {
             MOBILEDB.addNewMobileType(mobile);
        });

        LOGGER.log(Level.INFO, "{0}", USERDB.toString());
        LOGGER.log(Level.INFO, "{0}", MOBILEDB.toString());
        
    }
}
