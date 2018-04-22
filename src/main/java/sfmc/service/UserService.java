package sfmc.service;

import sfmc.model.Authentication.ApiIntegrationSet;
import sfmc.model.Authentication.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    ApiIntegrationSet findApiSetByUserId(int userId);
    ApiIntegrationSet save(ApiIntegrationSet user);
}
