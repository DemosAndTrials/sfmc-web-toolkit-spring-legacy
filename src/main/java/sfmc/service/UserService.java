package sfmc.service;

import sfmc.model.Authentication.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
