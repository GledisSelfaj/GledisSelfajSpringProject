package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.controller.requests.BankAccountRequest;
import ProjectSpringBoot.Project.controller.requests.RequestRequest;
import ProjectSpringBoot.Project.view.RequestView;

import java.util.List;

public interface RequestService {
    List<RequestView> findAllRequests();
    RequestView update(RequestRequest newRequest);
    List<RequestView> getRequestByUserId(Integer userID);
    RequestView createRequest(BankAccountRequest request);
}
