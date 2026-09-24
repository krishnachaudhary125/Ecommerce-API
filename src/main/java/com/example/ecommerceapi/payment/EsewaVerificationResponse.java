package com.example.ecommerceapi.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EsewaVerificationResponse {

    private String productId;
    private String productName;
    private String totalAmount;
    private String code;
    private Message message;

    private TransactionDetails transactionDetails;

    private String merchantName;

    @Data
    public static class Message {

        private String technicalSuccessMessage;
        private String successMessage;
    }

    @Data
    public static class TransactionDetails {

        private String date;

        @JsonProperty("referenceId")
        private String referenceId;

        private String status;
    }
}