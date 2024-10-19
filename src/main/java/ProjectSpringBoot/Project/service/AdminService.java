package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.view.UserView;

public interface AdminService {


    UserView createBanker(UserRequest request);
    UserView updateBanker(UserRequest request);
    void deleteById(Integer Id);
}
