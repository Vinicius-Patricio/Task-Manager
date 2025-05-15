package task_manager.portifolio.vinicius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import task_manager.portifolio.vinicius.controller.dto.UserDTO;
import task_manager.portifolio.vinicius.model.User;
import task_manager.portifolio.vinicius.repository.UserRepository;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.email()).isPresent()){
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
    
        User userSave = userRepository.save(user);
    
        return ResponseEntity.ok(userSave);
    
        
    }
}
