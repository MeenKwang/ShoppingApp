package com.shopping.admin.repository;

import com.shopping.admin.dto.UserDTO;
import com.shopping.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    public User findByIdAndEmail(Integer id, String email);

    public Long countById(Integer id);

    public boolean existsById(Integer id);

    @Query("SELECT u from User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query("update User u set u.enabled = ?2 where u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.email LIKE %?1% AND u.firstName LIKE %?2% AND u.lastName LIKE %?3%")
    public Page<User> findAll(Pageable pageable, String email, String firstName, String lastName);
}
