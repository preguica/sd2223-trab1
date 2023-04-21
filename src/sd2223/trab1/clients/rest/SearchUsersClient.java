package sd2223.trab1.clients.rest;

import java.io.IOException;
import java.net.URI;

public class SearchUsersClient {
    static{
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static void main (String[] args) throws IOException {
        if(args.length != 2){
            System.err.println("Use: java sd2223.trab1.clients.rest.SearchUsersClient url userId");
            return;
        }

        String serverUrl = args[0];
        String userId = args[1];

        System.out.println("Sending request to server.");

        new RestUsersClient(URI.create(serverUrl)).searchUsers(userId);
    }
}
