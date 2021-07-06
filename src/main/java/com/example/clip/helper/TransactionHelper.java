package com.example.clip.helper;

import com.example.clip.dto.*;
import com.example.clip.model.CardData;
import com.example.clip.model.ClipUser;
import com.example.clip.model.Disbursement;
import com.example.clip.model.Payload;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionHelper {

    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    public static CardData convertCardDataDTOtoCardData(PayloadRequestDTO payloadRequestDTO) {
        CardData cardData = new CardData();
        CardDataDTO cardDataDTO = payloadRequestDTO.getCardData();
        cardData.setCardNumber(cardDataDTO.getCardNumber());
        cardData.setCardType(cardDataDTO.getCardType());
        cardData.setExpMonth(cardDataDTO.getExpMonth());
        cardData.setExpYear(cardDataDTO.getExpYear());

        return cardData;
    }


    public static ClipUser convertClipUserDTOtoClipUser(PayloadRequestDTO payloadRequestDTO) {

        ClipUser clipUser = new ClipUser();
        ClipUserDTO clipUserDTO = payloadRequestDTO.getClipUser();
        clipUser.setUserName(clipUserDTO.getUserName());

        return clipUser;
    }

    public static List<DisbursementDTO> buildDisbursementDTO(List<Disbursement> disbursements,
                                                             String clipUserName) {

        List<DisbursementDTO> disbursementDTOS = new ArrayList<>();

        if (disbursements != null && !disbursements.isEmpty()) {
            for (Disbursement disbursement : disbursements) {
                DisbursementDTO disbursementDTO = new DisbursementDTO();
                disbursementDTO.setClipUserName(clipUserName);
                disbursementDTO.setAmount(disbursement.getAmount());
                String date = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY).format(disbursement.getDate());
                disbursementDTO.setDate(date);
                disbursementDTOS.add(disbursementDTO);
            }

        }
        return disbursementDTOS;
    }

    public static List<PayloadResponseDTO> buildPayloadResponseDTOs(List<Payload> transactions) {

        List<PayloadResponseDTO> payloadResponseDTOS = new ArrayList<>();

        for (Payload transaction : transactions) {

            PayloadResponseDTO payloadResponseDTO = new PayloadResponseDTO();
            payloadResponseDTO.setAmount(transaction.getAmount());

            ClipUserDTO clipUserDTO = new ClipUserDTO();
            clipUserDTO.setUserName(transaction.getClipUser().getUserName());
            payloadResponseDTO.setClipUser(clipUserDTO);
            String date = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY).format(transaction.getDate());
            payloadResponseDTO.setDate(date);

            CardDataDTO cardDataDTO = new CardDataDTO();
            CardData cardData = transaction.getCardData();
            cardDataDTO.setCardNumber(cardData.getCardNumber());
            cardDataDTO.setCardType(cardData.getCardType());
            cardDataDTO.setExpMonth(cardData.getExpMonth());
            cardDataDTO.setExpYear(cardData.getExpMonth());
            payloadResponseDTO.setCardData(cardDataDTO);

            payloadResponseDTOS.add(payloadResponseDTO);
        }

        return payloadResponseDTOS;
    }

    public static DisbursementDTO buildDisbursementDTO(List<Payload> transactions,
                                                       ClipUser clipUser) {

        BigDecimal disbursementAmount = transactions.stream().map(Payload::getAmount)
                .reduce(BigDecimal::add).get();
        DisbursementDTO disbursementDTO = new DisbursementDTO();
        disbursementDTO.setAmount(disbursementAmount);
        disbursementDTO.setClipUserName(clipUser.getUserName());

        return disbursementDTO;

    }

    public static Disbursement buildDisbursement(DisbursementDTO disbursementDTO,
                                                 ClipUser clipUser) {

        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(disbursementDTO.getAmount());
        disbursement.setClipUser(clipUser);

        return disbursement;
    }

    public static Payload buildPayload(ClipUser clipUserDB, PayloadRequestDTO payloadRequestDTO) {

        ClipUser clipUser = convertClipUserDTOtoClipUser(payloadRequestDTO);
        Payload payload = new Payload();

        if (clipUserDB != null) {
            payload.setClipUser(clipUserDB);
        } else {
            payload.setClipUser(clipUser);
        }

        CardData cardData = TransactionHelper.convertCardDataDTOtoCardData(payloadRequestDTO);
        payload.setCardData(cardData);
        payload.setFlagDisbursement(0);
        payload.setAmount(new BigDecimal(payloadRequestDTO.getAmount()));

        return payload;
    }

    public static List<Payload> getTransactionsDisbursement(List<Payload> transactions) {

        List<Payload> transactionsDisbursement = new ArrayList<>();

        for (Payload transaction : transactions) {
            if (transaction.getFlagDisbursement() == 0) {
                transactionsDisbursement.add(transaction);
            }
        }

        return transactionsDisbursement;
    }
}