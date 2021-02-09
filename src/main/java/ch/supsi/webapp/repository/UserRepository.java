package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findUserByUsername(String username);

}
