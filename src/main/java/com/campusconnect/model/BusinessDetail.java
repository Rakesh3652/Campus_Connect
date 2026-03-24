package com.campusconnect.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetail {

    //contact detils of organizer

    private String businessName;
    private String businessEmail;
    private String businessMobile;
}
