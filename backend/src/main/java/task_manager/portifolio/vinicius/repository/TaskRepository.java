package task_manager.portifolio.vinicius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import task_manager.portifolio.vinicius.model.Task;
import task_manager.portifolio.vinicius.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findByUser(User user);
    Optional<Task> findByIdAndUser(Long id, User user);

    //Querys personalizadas afim de fazer buscar mais completas, que  o JPA não consegue, por exemplo buscar um titulo desconsiderando case sensitive;
    @Query("SELECT t FROM Task t WHERE t.user = :user AND LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> findByUserAndTitleContaining(@Param("user") User user, 
    @Param("keyword") String keyword);
    
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> findByUserIdAndTitleContaining(@Param("userId") Long userId,
    @Param("keyword") String keyword);
    

}
