package com.midgard.repository;

import com.midgard.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


}
