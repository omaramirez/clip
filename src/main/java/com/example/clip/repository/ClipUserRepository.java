package com.example.clip.repository;

import com.example.clip.model.ClipUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClipUserRepository extends JpaRepository<ClipUser, Long> {

    ClipUser findByUserName(String userName);

}