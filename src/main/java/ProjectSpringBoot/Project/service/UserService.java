package ProjectSpringBoot.Project.service;


import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.view.UserView;

public interface UserService {
    UserView save(UserRequest request);
    void deleteById(Integer Id);
    UserView findById(Integer Id);
}
