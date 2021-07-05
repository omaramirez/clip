package com.example.clip.repository;

import com.example.clip.model.ClipUser;
import com.example.clip.model.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayloadRepository extends JpaRepository<Payload, Long> {

    List<Payload> findByClipUser(ClipUser clipUser);
}