package com.example.clip.repository;


import java.util.List;

import com.example.clip.model.ClipUser;
import com.example.clip.model.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    List<Disbursement> findByClipUser(ClipUser clipUser);

}