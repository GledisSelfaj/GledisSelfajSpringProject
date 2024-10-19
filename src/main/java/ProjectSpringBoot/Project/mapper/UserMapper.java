package ProjectSpringBoot.Project.mapper;

import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.view.UserView;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserView mapEntityToView(User user) {
        return UserView.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public User mapRequestToEntity(UserRequest userRequest) {
        return User.builder()
                .Id(userRequest.getId())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .roles(userRequest.getRoles())
                .build();
    }
}
