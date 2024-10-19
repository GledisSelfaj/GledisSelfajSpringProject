package ProjectSpringBoot.Project.service;

import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var banker = User.builder()
                .username("bbbb")
                .password("123")
                .roles(Roles.BANKER)
                .build();
        userRepository.save(banker);

    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void create_Banker() {
        var userRequest = UserRequest.builder()
                .username("Admin")
                .password("Admin")
                .roles(Roles.BANKER)
                .build();
        var newBanker = adminService.createBanker(userRequest);
        assertThat(newBanker).isNotNull();
        assertThat(newBanker.getUsername()).isEqualTo("Admin");
        assertThat(newBanker.getRoles()).isEqualTo(Roles.BANKER);
        System.out.println(newBanker);
    }

    @Test
    void delete_Banker_By_Id() {
        var user = User.builder()
                .username("alisa")
                .roles(Roles.BANKER)
                .password("nb")
                .build();
        user=userRepository.save(user);
        adminService.deleteById(user.getId());
        assertThat(userRepository.existsById(1)).isFalse();
        System.out.println("Delete Banker By Id");
    }

    @Test
    void update_banker() {
        var userRequest = UserRequest.builder()
                .username("Admin")
                .password("Admin")
                .roles(Roles.BANKER)
                .build();
        var banker = adminService.updateBanker(userRequest);
        assertThat(banker).isNotNull();
        assertThat(banker.getRoles()).isEqualTo(Roles.BANKER);
    }
}
