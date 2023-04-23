package sd2223.trab1.clients.rest;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public class DeleteUserClient {

    private static Logger Log = Logger.getLogger(CreateUserClient.class.getName());

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println("Use: java sd2223.trab1.clients.rest.DeleteUserClient url name pwd");
            return;
        }

        String serverUrl = args[0];
        String name = args[1];
        String pwd = args[2];

        Log.info("Sending request to server.");

        var result = new RestUsersClient(URI.create(serverUrl)).deleteUser(name, pwd);
        System.out.println("Result: " + result);
    }
}


/**
 * import sd2223.trab1.api.User;
 * <p>
 * import java.io.IOException;
 * import java.net.URI;
 * import java.util.logging.Logger;
 * <p>
 * public class CreateUserClient {
 * <p>
 * private static Logger Log = Logger.getLogger(CreateUserClient.class.getName());
 * <p>
 * static{
 * System.setProperty("java.net.preferIPv4Stack", "true");
 * }
 * <p>
 * public static void main(String[] args) throws IOException {
 * <p>
 * if(args.length != 5){
 * System.err.println("Use: java sd2223.trab1.clients.rest.CreateUserClient url name pwd domain displayName");
 * return;
 * }
 * <p>
 * String serverUrl = args[0];
 * String name = args[1];
 * String pwd = args[2];
 * String domain = args[3];
 * String displayName = args[4];
 * <p>
 * User u = new User(name, pwd, domain, displayName);
 * <p>
 * Log.info("Sending request to server.");
 * <p>
 * var result = new RestUsersClient(URI.create(serverUrl)).createUser(u);
 * System.out.println("Result: " + result);
 * }
 * }
 */