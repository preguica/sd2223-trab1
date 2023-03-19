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

public class PostFeedClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 7) {
            System.err.println("Use: java trab1.feeds.PostFeedClient url userID password id user domain text");
            return;
        }

        String serverUrl = args[0];
        String userId = args[1];
        String pwd = args[2];
        long id = Long.parseLong(args[3]);
        String user = args[4];
        String domain = args[5];
        String text = args[6];

        Message msg = new Message(id, user, domain, text);

        System.out.println("Sending request to server.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(serverUrl).path(FeedsService.PATH);

        Response r = target.path(userId)
                .queryParam(FeedsService.PWD, pwd)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(msg, MediaType.APPLICATION_JSON));

        if (r.getStatus() == Status.OK.getStatusCode() && r.hasEntity())
            System.out.println("Success, post on feed with id: " + r.readEntity(String.class));
        else
            System.out.println("Error, HTTP error status: " + r.getStatus());

    }
}
