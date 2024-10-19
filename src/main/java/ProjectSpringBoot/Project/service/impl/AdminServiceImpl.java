package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.exception.NoBankerFoundException;
import ProjectSpringBoot.Project.service.AdminService;
import ProjectSpringBoot.Project.service.UserService;
import ProjectSpringBoot.Project.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    @Override
    public UserView createBanker(@RequestBody UserRequest bankerRequest) {
        bankerRequest.setRoles(Roles.BANKER);
        return userService.save(bankerRequest);
    }

    @Override
    public UserView updateBanker(UserRequest request) {
        if (request.getRoles().equals(Roles.BANKER)) {
            return userService.save(request);
        } else {
            throw new NoBankerFoundException("No banker found");
        }
    }

    @Override
    public void deleteById(@PathVariable Integer Id) {
        var userDb = userService.findById(Id);
        if (userDb.getRoles().equals(Roles.BANKER)) {
            userService.deleteById(Id);
        } else {
            throw new NoBankerFoundException("No banker found with id " + Id);
        }

    }
}
