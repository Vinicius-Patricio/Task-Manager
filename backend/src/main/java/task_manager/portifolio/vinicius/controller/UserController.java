package task_manager.portifolio.vinicius.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import task_manager.portifolio.vinicius.controller.dto.UserDTO;
import task_manager.portifolio.vinicius.controller.dto.mapper.UserMapper;
import task_manager.portifolio.vinicius.model.User;
import task_manager.portifolio.vinicius.repository.UserRepository;
import task_manager.portifolio.vinicius.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println("Senha original recebida: " + userDTO.password());
    
        String encodedPassword = passwordEncoder.encode(userDTO.password());
        System.out.println("Senha criptografada: " + encodedPassword);
        
        User user = userMapper.toEntity(userDTO);
        user.setPassword(encodedPassword);
        
        User savedUser = userService.saveUser(user);
        System.out.println("Senha salva no banco: " + savedUser.getPassword());
        
        return ResponseEntity.ok(userMapper.toDTO(savedUser));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            User updatedUser = userService.updateUser(id, user);
            UserDTO updatedUserDTO = userMapper.toDTO(updatedUser);
            
            return ResponseEntity.ok(updatedUserDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            UserDTO userDTO = userMapper.toDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) {
        try {
            User user = userService.findUserByName(name);
            UserDTO userDTO = userMapper.toDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
