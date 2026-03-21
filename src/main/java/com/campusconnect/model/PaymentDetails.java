package com.campusconnect.model;

import com.campusconnect.domain.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDetails {
    
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
    private PaymentStatus status;
}
