package ProjectSpringBoot.Project.mapper;

import ProjectSpringBoot.Project.entity.Request;
import ProjectSpringBoot.Project.view.RequestView;
import org.springframework.stereotype.Service;

@Service
public class RequestMapper {
    public RequestView mapEntityToView(Request request) {
        return RequestView.builder()
                .id(request.getId())
                .status(request.getStatus())
                .feedback(request.getFeedback())
                .salary(request.getSalary())
                .bankAccount(request.getBankAccount())
                .build();
    }
}
