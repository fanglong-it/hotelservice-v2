package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.Role;
import fiveman.hotelservice.entities.User;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.RoleRepository;
import fiveman.hotelservice.repository.UserRepository;
import fiveman.hotelservice.request.UserRequest;
import fiveman.hotelservice.response.UserResponse;
import fiveman.hotelservice.security.JwtTokenProvider;
import fiveman.hotelservice.service.UserService;
import fiveman.hotelservice.utils.Common;
import fiveman.hotelservice.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User saveUser(User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findRoleByName(Common.USER));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        roleRepository.save(role);
        return roleRepository.findRoleByName(role.getName());
    }

    @Override
    public String addRoleToUser(String username, String roleName) {
        if(userRepository.existsByUsername(username) || !roleName.equals(Common.ADMIN)){
            User user = userRepository.findUserByUsername(username);
            Role role = roleRepository.findRoleByName(roleName);
            List<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }else{
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Can't found username!");
        }

        return username;
    }


    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserResponse userResponse  = modelMapper.map(users.get(i), UserResponse.class);
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public User whoami(HttpServletRequest request) {
            return userRepository.findUserByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findUserByUsername(username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return jwtTokenProvider.createToken(username, authorities);
        } catch (AuthenticationException e) {
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Invalid username/password supplied");
        }
    }

    @Override
    public UserRequest signup(UserRequest request) {
    	User user = UserServiceImpl.MapUserRequestToUser(request);
            if (userRepository.existsByUsername(user.getUsername())) {
            	throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Username is already in use");

//                List<Role> roles = new ArrayList<>();
//                List<Role> userRoleInsert = user.getRoles();
//                for (int i = 0; i < userRoleInsert.size(); i++) {
//                    roles.add(roleRepository.findRoleByName(userRoleInsert.get(i).getName()));
//                }
//                user.setRoles(roles);
                
//                Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
//                user.getRoles().forEach(role -> {
//                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//                });
//                return jwtTokenProvider.createToken(user.getUsername(), grantedAuthorities);
            }
            String name = Utilities.isEmptyString(user.getName()) ? Common.USER_NAME : user.getName();
            user.setName(name);
            //set default role for user
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findRoleByName(Common.USER));
            user.setRoles(roles);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUsername(user.getUsername());
            userRepository.save(user);
            
            return request;
    }

    @Override
    public String refresh(String username) {
        User user = userRepository.findUserByUsername(username);
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return jwtTokenProvider.createToken(username, grantedAuthorities);
    }
    
    public static User MapUserRequestToUser(UserRequest user) {
    	return new User(0,user.getName(),user.getUserName(),user.getPassword(),null);
    }

	@Override
	public String setRoleAdmin(String userName, String roleName) {
		if(userRepository.existsByUsername(userName) && roleName.equals(Common.ADMIN)){
            User user = userRepository.findUserByUsername(userName);
            Role role = roleRepository.findRoleByName(userName);
            List<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }else{
            throw new AppException(HttpStatus.NOT_FOUND.value(), "Can't found username!");
        }

        return userName;
	}
}
