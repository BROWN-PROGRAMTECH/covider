package cm.ubuea.covider.registration.service.mapper;

import org.springframework.stereotype.Service;

import cm.ubuea.covider.registration.domain.Role;
import cm.ubuea.covider.registration.domain.User;
import cm.ubuea.covider.registration.service.dto.UserDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setIdNumber(userDTO.getIdNumber());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Role> roles = this.rolesFromStrings(userDTO.getRoles());
            user.setRoles(roles);
            return user;
        }
    }


    private Set<Role> rolesFromStrings(Set<String> rolesAsString) {
        Set<Role> authorities = new HashSet<>();

        if (rolesAsString != null) {
            authorities = rolesAsString.stream().map(string -> {
                Role auth = new Role();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
