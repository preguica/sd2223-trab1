package sd2223.trab1.servers.java;


import sd2223.trab1.api.Message;
import sd2223.trab1.api.User;
import sd2223.trab1.api.java.Feeds;
import sd2223.trab1.api.java.Result;

import java.util.*;
import java.util.logging.Logger;

public class JavaFeeds implements Feeds {
    private final Map<String, LinkedList<Message>> feeds = new HashMap<>();

    private static Logger Log = Logger.getLogger(JavaUsers.class.getName());
    public static String DOMAIN;
    public static Random SEED;

    @Override
    public Result<Long> postMessage(String user, String pwd, Message msg){
        Log.info("postMessage: "+ msg);
        //Check if user data is valid
        if(user == null || pwd == null || msg == null){
            Log.info("Format invalid");
            return Result.error(Result.ErrorCode.BAD_REQUEST);
        }
        var list = new LinkedList<Message>();
        if(feeds.containsKey(user)){
            list = feeds.get(user);
        }
        msg.setId(SEED.nextLong()& Integer.MAX_VALUE);
        list.add(msg);

        feeds.put(user, list);

        return Result.ok(msg.getId());
    }

    @Override
    public Result<Void> removeFromPersonalFeed(String user, long mid, String pwd){
        Log.info("getMessage : messageid= " + mid +" user = " + user + "; pwd = " + pwd);
        //Check if user data is valid
        if(user == null || pwd == null){
            Log.info("Name or password invalid.");
            return Result.error(Result.ErrorCode.BAD_REQUEST);
        }

        //getUserService or check if the password is correct
        User userObj = new User();//getUser

        LinkedList<Message> feed = feeds.get(user);
        //Check if users exist
        if(feed == null){
            Log.info("User does not have feed.");
            return Result.error(Result.ErrorCode.NOT_FOUND);
        }

        //Check if the password is correct
        if(!userObj.getPwd().equals(pwd)){
            Log.info("Password is incorrect.");
            return Result.error(Result.ErrorCode.FORBIDDEN);
        }

        return Result.ok();
    }

    @Override
    public Result<Message> getMessage(String user, long mid) { return null;}

    @Override
    public Result<List<Message>> getMessages(String user, long time) { return null;}

    @Override
    public Result<Void> subUser(String user, String userSub, String pwd) { return null;}

    @Override
    public Result<Void> unsubscribeUser(String user, String userSub, String pwd) { return null;}

    @Override
    public Result<List<String>> listSubs(String user) { return null;}

}
