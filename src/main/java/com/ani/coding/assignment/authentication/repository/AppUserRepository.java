package com.ani.coding.assignment.authentication.repository;

import com.ani.coding.assignment.authentication.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("select u from AppUser u where userName=?1 and password=?2")
    Optional<AppUser> signIn(String userName, String password);
}
