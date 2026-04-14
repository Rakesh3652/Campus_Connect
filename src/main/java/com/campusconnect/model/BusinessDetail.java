package com.campusconnect.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetail {

    //contact detils of organizer

    private String businessName;
    private String businessEmail;
    private String businessMobile;
}
