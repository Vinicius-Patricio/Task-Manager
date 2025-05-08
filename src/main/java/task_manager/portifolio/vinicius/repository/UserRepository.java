package task_manager.portifolio.vinicius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import task_manager.portifolio.vinicius.model.User;

//Próprio JpaRepository ja adiciona o CRUD simples nesse caso
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
    boolean existsByEmail (String email);

}
