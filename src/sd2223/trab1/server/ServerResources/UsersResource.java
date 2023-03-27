package sd2223.trab1.server.ServerResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import sd2223.trab1.api.User;
import sd2223.trab1.api.rest.UsersService;

public class UsersResource implements UsersService {

    private final Map<String, User> users = new HashMap<String, User>();

    public UsersResource() {
    }

    @Override
    public String createUser(User user) {
        if (user == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        String username = user.getName();

        if (users.containsKey(username)) {
            throw new WebApplicationException(Status.CONFLICT);
        }
        users.put(username, user);
        return username;
    }

    @Override
    public User getUser(String name, String pwd) {
        if (name == null || pwd == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

        User user = users.get(name);
        if (user == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        if (user.getPwd() != pwd) {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        return user;
    }

    @Override
    public User updateUser(String name, String pwd, User user) {
        if (name == null || pwd == null || user == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

        User oldUser = users.get(name);
        if (oldUser == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        if (oldUser.getPwd() != pwd) {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        users.remove(name);
        users.put(user.getName(), user);
        return user;
    }

    @Override
    public User deleteUser(String name, String pwd) {
        if (name == null || pwd == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

        User user = users.get(name);
        if (user == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        if (user.getPwd() != pwd) {
            throw new WebApplicationException(Status.FORBIDDEN);
        }

        return users.remove(name);
    }

    @Override
    public List<User> searchUsers(String pattern) {
        List<User> res = new ArrayList<User>();
        Iterator<User> it = users.values().iterator();
        while (it.hasNext()) {
            User user = it.next();
            String name = user.getName();
            if (name.toLowerCase().contains(pattern.toLowerCase())) {
                res.add(new User(name, "", user.getDomain(), user.getDisplayName()));
            }
        }
        return res;
    }

}
