package com.example.ecommerceapi.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EsewaPaymentService {

    private final RestTemplate restTemplate;
    private final String merchantId;
    private final String merchantSecret;

    private static final String VERIFICATION_URL =
            "https://rc.esewa.com.np/mobile/transaction";

    public EsewaPaymentService(
            @Value("${esewa.merchant-id}") String merchantId,
            @Value("${esewa.merchant-secret}") String merchantSecret
    ) {
        this.restTemplate = new RestTemplate();
        this.merchantId = merchantId;
        this.merchantSecret = merchantSecret;
    }

    public boolean verifyPayment(
            String refId,
            double expectedAmount
    ) {

        String url = UriComponentsBuilder
                .fromUriString(VERIFICATION_URL)
                .queryParam("txnRefId", refId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();

        headers.set("merchantId", merchantId);
        headers.set("merchantSecret", merchantSecret);

        HttpEntity<Void> requestEntity =
                new HttpEntity<>(headers);

        ResponseEntity<EsewaVerificationResponse[]> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        EsewaVerificationResponse[].class
                );

        EsewaVerificationResponse[] payments =
                response.getBody();

        if (payments == null || payments.length == 0) {
            return false;
        }

        EsewaVerificationResponse payment =
                payments[0];

        if (payment.getTransactionDetails() == null) {
            return false;
        }

        String status =
                payment.getTransactionDetails().getStatus();

        if (!"COMPLETE".equalsIgnoreCase(status)) {
            return false;
        }

        double paidAmount;

        try {
            paidAmount =
                    Double.parseDouble(payment.getTotalAmount());
        } catch (Exception e) {
            return false;
        }

        double roundedPaidAmount =
                Math.round(paidAmount * 100.0) / 100.0;

        double roundedExpectedAmount =
                Math.round(expectedAmount * 100.0) / 100.0;

        return Double.compare(
                roundedPaidAmount,
                roundedExpectedAmount
        ) == 0;
    }
}