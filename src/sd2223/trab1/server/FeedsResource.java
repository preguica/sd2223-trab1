package sd2223.trab1.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.Message;
import sd2223.trab1.api.User;
import sd2223.trab1.api.rest.FeedsService;
import sd2223.trab1.api.rest.UsersService;

public class FeedsResource implements FeedsService {

    private Map<String, Map<Long, Message>> feedList = new HashMap<String, Map<Long, Message>>();

    private Map<String, List<User>> usersSubs = new HashMap<String, List<User>>();

    private static Logger Log = Logger.getLogger(UsersResource.class.getName());

    private UsersService users = new UsersResource();

    public FeedsResource() {
    }

    @Override
    public long postMessage(String user, String pwd, Message msg) {
        if (user == null || pwd == null || msg == null) {
            Log.info("User, password or message is null!");
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        User u = null;
        try {
            // Nesta função já são verificados se o user existe e se a pwd está correta
            u = users.getUser(user, pwd);
        } catch (WebApplicationException e) {
            Log.info("User server request failed!");
            throw new WebApplicationException(Status.FORBIDDEN);
        } catch (Exception e) {
            Log.info("Ups! Something went wrong.\n" + e.getMessage());
            // ! ALGO ACONTECEU!
        }

        if (feedList.containsKey(user))
            feedList.get(user).put(msg.getId(), msg);
        else {
            Map<Long, Message> list = new HashMap<Long, Message>();
            list.put(msg.getId(), msg);
            feedList.put(user, list);
        }
        Log.info(String.format("Message %d posted successfully by %s!", msg.getId(), user));
        return msg.getId();

    }

    @Override
    public void removeFromPersonalFeed(String user, long mid, String pwd) {
        if (user == null || pwd == null) {
            Log.info("User or password is null!");
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        User u = null;
        try {
            // Nesta função já são verificados se o user existe e se a pwd está correta
            u = this.getUserCall(user, pwd);
        } catch (WebApplicationException e) {
            Log.info("User server request failed!");
            throw new WebApplicationException(Status.FORBIDDEN);
        } catch (Exception e) {
            Log.info("Ups! Something went wrong.\n" + e.getMessage());
            // ! ALGO ACONTECEU!
        }

        String name = u.getName();
        if (!feedList.containsKey(name) || !feedList.get(name).containsKey(mid)) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        feedList.get(name).remove(mid);
        Log.info(String.format("Message %d removed successfully by %s!", mid, user));
    }

    @Override
    public Message getMessage(String user, long mid) {
        if (user == null) {
            Log.info("User is null!");
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        Map<Long, Message> uMsgs = feedList.get(user);

        if (uMsgs == null) {
            Log.info(String.format("User %s not found in feed!", user));
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        Message msg;
        if ((msg = uMsgs.get(mid)) == null) {
            Log.info(String.format("User %s does not have message %d in his feed!", user, mid));
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        Log.info(String.format("Message %d got successfully", mid));
        return msg;

    }

    @Override
    public List<Message> getMessages(String user, long time) {
        if (user == null) {
            Log.info("User is null!");
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        Map<Long, Message> uMsgs = feedList.get(user);

        if (uMsgs == null) {
            Log.info(String.format("User %s not found in feed!", user));
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        List<Message> msgs = new ArrayList<Message>(uMsgs.values());

        Predicate<Message> predicate = msg -> (msg.getId() <= time);
        msgs.removeIf(predicate);

        // !! A ordem não é mantida !!

        Log.info(String.format("Messages from %s got successfully", user));
        return msgs;
    }

    @Override
    public void subUser(String user, String userSub, String pwd) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subUser'");
    }

    @Override
    public void unsubscribeUser(String user, String userSub, String pwd) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unsubscribeUser'");
    }

    @Override
    public List<String> listSubs(String user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listSubs'");
    }

    private User getUserCall(String userID, String pwd) {
        return users.getUser(userID, pwd);
    }

}
