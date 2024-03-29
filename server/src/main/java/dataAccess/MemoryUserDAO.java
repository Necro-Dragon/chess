package dataAccess;

import Exceptions.*;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{
    /**
     * Key is the username (a String) and the value is the UserData
     * */
    HashMap<String, UserData> fakeUserInfoDatabase = new HashMap<>();

    @Override
    public void clear() {
        fakeUserInfoDatabase.clear();
    }

    @Override
    public UserData createUser(UserData userData) {
        fakeUserInfoDatabase.put(userData.username(), userData);
        return userData;
    }

    @Override
    public UserData getUserData(String username) {
        return fakeUserInfoDatabase.get(username);
    }

}
