package org.oneProjectOneMonth.lms.api.token.repository;

import org.oneProjectOneMonth.lms.api.token.model.Token;
import org.oneProjectOneMonth.lms.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUser(User user);
}
