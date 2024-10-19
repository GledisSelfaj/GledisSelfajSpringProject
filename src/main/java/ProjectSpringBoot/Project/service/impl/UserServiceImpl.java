package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.controller.requests.UserRequest;
import ProjectSpringBoot.Project.exception.UserNameExistException;
import ProjectSpringBoot.Project.mapper.UserMapper;
import ProjectSpringBoot.Project.repo.UserRepository;
import ProjectSpringBoot.Project.service.UserService;
import ProjectSpringBoot.Project.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserView save(UserRequest request) {
        var user = userMapper.mapRequestToEntity(request);
        var userFound= userRepository.findByUsername(user.getUsername());
        if(userFound.isPresent()) {
            throw new UserNameExistException("This Username already exist");
        }
        var userDb = userRepository.save(user);
        return userMapper.mapEntityToView(userDb);
    }

    @Transactional
    @Override
    public void deleteById(Integer Id) {
        var userDb = userRepository.findById(Id).get();
        userRepository.delete(userDb);
    }

    @Override
    public UserView findById(Integer Id) {
        var userDb = userRepository.findById(Id).get();
        return userMapper.mapEntityToView(userDb);
    }
}
