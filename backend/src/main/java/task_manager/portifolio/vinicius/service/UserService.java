package task_manager.portifolio.vinicius.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import task_manager.portifolio.vinicius.model.User;
import task_manager.portifolio.vinicius.repository.TaskRepository;
import task_manager.portifolio.vinicius.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Hibernate.initialize(user.getTasks());

        taskRepository.deleteAll(user.getTasks());
        
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

}
