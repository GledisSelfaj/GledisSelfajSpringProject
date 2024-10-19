package ProjectSpringBoot.Project.controller.requests;

import ProjectSpringBoot.Project.common.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private Integer id;
    private String username;
    private String password;
    private Roles roles;
}
