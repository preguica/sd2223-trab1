package sd2223.trab1.clients.rest;

import sd2223.trab1.api.User;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public class UpdateUserClient {

    private static Logger Log = Logger.getLogger(CreateUserClient.class.getName());

    static{
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static void main(String[] args) throws IOException {

        if(args.length != 5){
            System.err.println("Use: java sd2223.trab1.clients.rest.UpdateUserClient " +
                    "url name oldPwd domain displayName newPwd");
            return;
        }

        String serverUrl = args[0];
        String name = args[1];
        String oldPwd = args[2];
        String domain = args[3];
        String displayName = args[4];
        String newPwd = args[5];

        User u = new User(name, newPwd, domain, displayName);

        Log.info("Sending request to server.");

        var result = new RestUsersClient(URI.create(serverUrl)).updateUser(name, oldPwd, u);
        System.out.println("Result: " + result);
    }
}
