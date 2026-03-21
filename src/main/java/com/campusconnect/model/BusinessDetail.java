package com.campusconnect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetail {

    //contact detils of organizer

    private String businessName;
    private String businessEmail;
    private String businessMobile;
}
