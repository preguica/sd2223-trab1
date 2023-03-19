package sd2223.trab1.feeds;

import java.io.IOException;
import java.sql.Time;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.rest.FeedsService;

public class GetMessagesClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println("Use: java trab1.feeds.GetMessagesClient url userID time");
            return;
        }

        String serverUrl = args[0];
        String userID = args[1];
        long time = Long.parseLong(args[2]);

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(FeedsService.PATH);

        Response r = target.path(userID)
                .queryParam(FeedsService.TIME, time)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity())
            System.out.println(
                    String.format("Success, User %s, messages posted after %d: ", userID, time) // <- Transform time in
                                                                                                // long to time format
                            + r.readEntity(String.class));
        else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }
}
