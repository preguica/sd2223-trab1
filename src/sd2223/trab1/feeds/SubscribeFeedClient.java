package sd2223.trab1.feeds;

import java.io.IOException;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.Message;
import sd2223.trab1.api.rest.FeedsService;

public class SubscribeFeedClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 4) {
            System.err.println("Use: java trab1.feeds.SubscribeFeedClient url userID userSubscribed password");
            return;
        }

        String serverUrl = args[0];
        String userId = args[1];
        String userSubs = args[2];
        String pwd = args[3];

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(FeedsService.PATH);

        Response r = target.path(userId).path(userSubs)
                .queryParam(FeedsService.PWD, pwd)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(null); // <- ?????

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity())
            System.out.println(
                    String.format("Success, User %s subscribed User %s: ", userId, userSubs)
                            + r.readEntity(String.class));
        else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }
}
