package com.egovframework.emes.repository.user;

import com.egovframework.emes.domain.user.User;
import com.egovframework.emes.domain.user.id.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {

  Optional<User> findByIdAndPassword(UserId userId, String password);
}
