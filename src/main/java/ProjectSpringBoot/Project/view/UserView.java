package ProjectSpringBoot.Project.view;

import ProjectSpringBoot.Project.common.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserView{

    private Integer id;
    private String username;
    private Roles roles;
}
