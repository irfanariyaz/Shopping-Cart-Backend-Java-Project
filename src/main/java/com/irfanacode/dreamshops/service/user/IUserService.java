package com.irfanacode.dreamshops.service.user;

import com.irfanacode.dreamshops.dto.UserDto;
import com.irfanacode.dreamshops.model.User;
import com.irfanacode.dreamshops.request.*;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
