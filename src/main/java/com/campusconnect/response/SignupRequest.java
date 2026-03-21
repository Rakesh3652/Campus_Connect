package com.campusconnect.response;

import lombok.Data;

@Data
public class SignupRequest {

    private String fullName;
    private String email;
    private String otp;

}
