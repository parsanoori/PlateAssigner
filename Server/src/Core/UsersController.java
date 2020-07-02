package Core;

import Exceptions.UserAlreadyExistsException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class UsersController {

    static private HashMap<String, User> ids = new HashMap<>();
    public static Vector<User> onlineUsers = new Vector<>();

    static synchronized public void addUser(User user) throws UserAlreadyExistsException {
        Objects.requireNonNull(user);
        if (ids.get(user.id) != null)
            throw new UserAlreadyExistsException();
        ids.put(user.id, user);
    }

    static synchronized public User getUserByID(String id){
        Objects.requireNonNull(id);
        return ids.get(id);
    }

    static public Collection<User> getUsers(){
        return ids.values();
    }
}
