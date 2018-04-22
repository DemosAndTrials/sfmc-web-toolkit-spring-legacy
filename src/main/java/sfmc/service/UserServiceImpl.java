package sfmc.service;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sfmc.model.Authentication.ApiIntegrationSet;
import sfmc.model.Authentication.Role;
import sfmc.model.Authentication.User;
import sfmc.repository.ApiIntegrationSetRepository;
import sfmc.repository.RoleRepository;
import sfmc.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;
    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;
    @Qualifier("apiIntegrationSetRepository")
    @Autowired
    private ApiIntegrationSetRepository apiIntegrationSetRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public ApiIntegrationSet findApiSetByUserId(int userId){
        return apiIntegrationSetRepository.findByUserId(userId);
    }

    @Override
    public ApiIntegrationSet save(ApiIntegrationSet set) {
        return apiIntegrationSetRepository.save(set);
    }

}