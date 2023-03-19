package sd2223.trab1.clients;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.User;
import sd2223.trab1.api.rest.UsersService;

public class GetUserClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println("Use: java trab1.clients.GetUserClient url name password");
            return;
        }

        String serverUrl = args[0];
        String name = args[1];
        String password = args[2];

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(UsersService.PATH);

        Response r = target.path(name)
                .queryParam(UsersService.PWD, password).request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity()) {
            System.out.println("Success:");
            User user = r.readEntity(User.class);
            System.out.println("User : " + user);
        } else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }

}
