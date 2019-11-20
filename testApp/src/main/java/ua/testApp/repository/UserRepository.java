package ua.testApp.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.testApp.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    User findByUsername(String username);
    
}