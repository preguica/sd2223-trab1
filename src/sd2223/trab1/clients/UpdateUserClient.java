package sd2223.trab1.clients;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.User;
import sd2223.trab1.api.rest.UsersService;

public class UpdateUserClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 6) {
            System.err.println("Use: java aula2.clients.UpdateUserClient url userId oldpwd pwd domain displayName");
            return;
        }

        String serverUrl = args[0];
        String name = args[1];
        String oldpwd = args[2];
        String pwd = args[3];
        String domain = args[4];
        String displayName = args[5];

        var user = new User(name, pwd, domain, displayName);

        System.out.println("Sending request to server.");

        // TODO complete this client code

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(UsersService.PATH);

        Response r = target.path(name)
                .queryParam(UsersService.PWD, oldpwd)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity()) {
            System.out.println("Success:");
            var u = r.readEntity(User.class);
            System.out.println("Updated user: " + u);
        } else
            System.out.println("Error, HTTP error status: " + r.getStatus());
    }
}
