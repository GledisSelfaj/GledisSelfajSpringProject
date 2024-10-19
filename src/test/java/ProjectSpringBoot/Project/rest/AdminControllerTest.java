package ProjectSpringBoot.Project.rest;

import ProjectSpringBoot.Project.common.Roles;
import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.entity.User;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.view.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;


    @BeforeEach
    void setUp() {
        var user = User.builder()
                .username("DREJTORI")
                .password("{noop}123456")
                .roles(Roles.ADMIN)
                .build();
       this.user= userRepository.save(user);
    }
    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }
    @Test
    public void create_banker() throws Exception {
        var userRequest = UserRequest.builder()
                .username("g")
                .password("1234")
                .roles(Roles.BANKER)
                .build();
        var userView = UserView.builder()
                .id(null)
                .username("g")
                .roles(Roles.BANKER)
                .build();

      MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/banker/create")
                        .header(HttpHeaders.AUTHORIZATION, "Basic RFJFSlRPUkk6MTIzNDU2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
      String JsonResponse= result.getResponse().getContentAsString();
      System.out.println(JsonResponse);
      UserView userViewResponse= objectMapper.readValue(JsonResponse, UserView.class);
        assertNotNull(userViewResponse.getId());
    }

    @Test
    public void update_banker() throws Exception {
        var userRequest = UserRequest.builder()
                .id(user.getId())
                .username("Gled")
                .password("1200")
                .roles(Roles.BANKER)
                .build();
        var userView = UserView.builder()
                .id(userRequest.getId())
                .username("Gled")
                .roles(Roles.BANKER)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/banker/update")
                        .header(HttpHeaders.AUTHORIZATION, "Basic RFJFSlRPUkk6MTIzNDU2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userView)));
    }

    @Test
    public void delete_banker() throws Exception {
        var banker = User.builder()
                .username("Banker")
                .password("{noop}123")
                .roles(Roles.BANKER)
                .build();
        userRepository.save(banker);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/banker/delete/"+banker.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Basic RFJFSlRPUkk6MTIzNDU2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

