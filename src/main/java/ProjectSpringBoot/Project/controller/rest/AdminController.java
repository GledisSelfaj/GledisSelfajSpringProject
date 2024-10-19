package ProjectSpringBoot.Project.controller.rest;

import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.service.AdminService;
import ProjectSpringBoot.Project.utils.AppConstants;
import ProjectSpringBoot.Project.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(AppConstants.API_ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(AppConstants.CREATE_BANKER)
    public ResponseEntity<UserView> createBanker(@RequestBody UserRequest banker) {
        return ResponseEntity.ok(adminService.createBanker(banker));
    }

    @DeleteMapping(AppConstants.DELETE_BANKER)
    public ResponseEntity<UserView> deleteBanker(@PathVariable Integer id) {;
        adminService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping(AppConstants.UPDATE_BANKER)
    public ResponseEntity<UserView> updateBanker(@RequestBody UserRequest banker) {
        return ResponseEntity.ok(adminService.updateBanker(banker));
    }
}