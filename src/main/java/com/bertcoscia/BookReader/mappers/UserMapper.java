package com.bertcoscia.BookReader.mappers;

import com.bertcoscia.BookReader.dto.requests.UserRequest;
import com.bertcoscia.BookReader.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UserMapper {

    @Autowired
    PasswordEncoder bcrypt;

    public User toEntity(UserRequest request) {
        User user = new User();

        user.setName(request.name());
        user.setSurname(request.surname());
        user.setEmail(request.email());
        user.setPassword(bcrypt.encode(request.password()));
        user.setUsername(request.username());

        String encodedName;
        String encodedSurname;
        encodedName = URLEncoder.encode(user.getName(), StandardCharsets.UTF_8);
        encodedSurname = URLEncoder.encode(user.getSurname(), StandardCharsets.UTF_8);
        String defaultAvatarPrefix = "https://ui-avatars.com/api/?name=";
        String defaultAvatarBackground = "&background=0D8ABC&color=fff&size=512";
        user.setUrlAvatar(defaultAvatarPrefix + encodedName + "+" + encodedSurname + defaultAvatarBackground);

        return user;
    }

    public User update(User userToUpdate, User userUpdated) {
        userToUpdate.setName(userUpdated.getName());
        userToUpdate.setSurname(userUpdated.getSurname());
        userToUpdate.setEmail(userUpdated.getEmail());
        userToUpdate.setUsername(userUpdated.getUsername());

        String defaultAvatarPrefix = "https://ui-avatars.com/api/?name=";
        String defaultAvatarBackground = "&background=0D8ABC&color=fff&size=512";
        boolean nameOrSurnameChanged = !userToUpdate.getName().equals(userUpdated.getName()) || !userToUpdate.getSurname().equals(userUpdated.getSurname());
        if (userToUpdate.getUrlAvatar().startsWith(defaultAvatarPrefix) && nameOrSurnameChanged) {
            String encodedName = URLEncoder.encode(userUpdated.getName(), StandardCharsets.UTF_8);
            String encodedSurname = URLEncoder.encode(userUpdated.getSurname(), StandardCharsets.UTF_8);
            userToUpdate.setUrlAvatar(defaultAvatarPrefix + encodedName + "+" + encodedSurname + defaultAvatarBackground);
        }

        return userToUpdate;
    }
}
