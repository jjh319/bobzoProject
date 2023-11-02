package org.zerock.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(String userId);

    boolean existsByUserId(String id);
} // end interface
