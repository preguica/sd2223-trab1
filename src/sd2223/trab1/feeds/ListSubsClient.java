package sd2223.trab1.feeds;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.rest.FeedsService;

public class ListSubsClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Use: java trab1.feeds.ListSubsClient url userID");
            return;
        }

        String serverUrl = args[0];
        String userId = args[1];

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(FeedsService.PATH);

        Response r = target.path(userId)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity())
            System.out.println(
                    String.format("Success, List of user %s's subscriber: ", userId)
                            + r.readEntity(String.class));
        else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }
}
