package com.example.clip.controller;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import com.example.clip.dto.ClipUserDTO;
import com.example.clip.dto.DisbursementDTO;
import com.example.clip.dto.PayloadRequestDTO;
import com.example.clip.dto.PayloadResponseDTO;
import com.example.clip.helper.TransactionHelper;
import lombok.extern.slf4j.Slf4j;
import com.example.clip.model.ClipUser;
import com.example.clip.model.Disbursement;
import com.example.clip.model.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.clip.repository.ClipUserRepository;
import com.example.clip.repository.DisbursementRepository;
import com.example.clip.repository.PayloadRepository;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

    public static final String CLIP_USER_NAME_DOES_NOT_FOUND = "Clip User Name does not found";
    public static final String CLIP_USER_NAME_DOES_NOT_HAVE_TRANSACTIONS = "Clip User Name does not have transactions";
    public static final String CLIP_USER_NAME_DOES_NOT_HAVE_DISBURSEMENTS = "Clip User Name does not have disbursements";
    public static final String ALL_TRANSACTIONS_OF_THE_USER_ARE_DISBURSEMENT = "All transactions of the user are disbursement";

    @Autowired
    PayloadRepository payloadRepository;

    @Autowired
    ClipUserRepository clipUserRepository;

    @Autowired
    DisbursementRepository disbursementRepository;

    @RequestMapping(value = "/createPayload", method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody PayloadRequestDTO payloadRequestDTO) {

        ClipUser clipUser = clipUserRepository
                .findByUserName(payloadRequestDTO.getClipUser().getUserName());

        Payload payload = TransactionHelper.buildPayload(clipUser, payloadRequestDTO);

        try {
            payloadRepository.save(payload);
            log.info("Payload Created Successfully");
            return new ResponseEntity(payloadRequestDTO, HttpStatus.CREATED);

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/transactions/{clipUserName}")
    public ResponseEntity getAllTransactionsByClipUser(
            @PathVariable("clipUserName") String clipUserName) {

        ClipUser clipUser = clipUserRepository.findByUserName(clipUserName);

        if (clipUser == null) {
            log.error("Clip User name does not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CLIP_USER_NAME_DOES_NOT_FOUND);
        }

        List<Payload> transactions = payloadRepository.findByClipUser(clipUser);

        if (transactions == null || transactions.isEmpty()) {
            log.error("Clip User Name does not have transactions");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CLIP_USER_NAME_DOES_NOT_HAVE_TRANSACTIONS);
        }

        List<PayloadResponseDTO> payloadResponseDTOS = TransactionHelper
                .buildPayloadResponseDTOs(transactions);
        return ResponseEntity.status(HttpStatus.OK).body(payloadResponseDTOS);

    }

    @RequestMapping(value = "/createDisbursement", method = RequestMethod.POST)
    public ResponseEntity createDisbursementByClipUser(
            @RequestBody ClipUserDTO clipUserDTO) {

        ClipUser clipUser = clipUserRepository.findByUserName(clipUserDTO.getUserName());

        if (clipUser == null) {
            log.error("Clip User name does not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CLIP_USER_NAME_DOES_NOT_FOUND);
        }

        List<Payload> transactions = payloadRepository.findByClipUser(clipUser);

        if (transactions == null || transactions.isEmpty()) {
            log.error("Clip User name does not have transactions");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    CLIP_USER_NAME_DOES_NOT_HAVE_TRANSACTIONS);
        }

        List<Payload> transactionsDisbursement = TransactionHelper.getTransactionsDisbursement(transactions);

        if (transactionsDisbursement == null || transactionsDisbursement.isEmpty()) {
            log.error("All transactions of the user are disbursement");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ALL_TRANSACTIONS_OF_THE_USER_ARE_DISBURSEMENT);
        }

        try {
            DisbursementDTO disbursementDTO = TransactionHelper
                    .buildDisbursementDTO(transactionsDisbursement, clipUser);
            Disbursement disbursement = TransactionHelper.buildDisbursement(disbursementDTO, clipUser);

            disbursementRepository.save(disbursement);

            for (Payload transaction : transactions) {
                transaction.setFlagDisbursement(1);
                payloadRepository.save(transaction);
            }

            log.info("Disbursement Created Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(disbursementDTO);

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/disbursements/{clipUserName}")
    public ResponseEntity getDisbursementByClipUser(
            @PathVariable("clipUserName") String clipUserName) {

        ClipUser clipUser = clipUserRepository.findByUserName(clipUserName);

        if (clipUser == null) {
            log.error("Clip User Name does not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CLIP_USER_NAME_DOES_NOT_FOUND);
        }

        List<Disbursement> disbursements = disbursementRepository.findByClipUser(clipUser);

        if (disbursements == null || disbursements.isEmpty()) {
            log.error("Clip User Name does not have disbursements");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CLIP_USER_NAME_DOES_NOT_HAVE_DISBURSEMENTS);
        }

        List<DisbursementDTO> disbursementDTOS = TransactionHelper
                .buildDisbursementDTO(disbursements, clipUser.getUserName());

        return new ResponseEntity(disbursementDTOS, HttpStatus.OK);
    }
}
