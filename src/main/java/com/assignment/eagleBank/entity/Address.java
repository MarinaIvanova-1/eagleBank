package com.assignment.eagleBank.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @NotBlank(message = "First line of address is required")
    private String line1;

    private String line2;

    private String line3;

    @NotBlank(message = "Town is required")
    private String town;

    @NotBlank(message = "County is required")
    private String county;

    @NotBlank(message = "Postcode is required")
    private String postcode;

    public Address setLine1(String line1) {
        this.line1 = line1;
        return this;
    }

    public Address setLine2(String line2) {
        this.line2 = line2;
        return this;
    }

    public Address setLine3(String line3) {
        this.line3 = line3;
        return this;
    }

    public Address setTown(String town) {
        this.town = town;
        return this;
    }

    public Address setCounty(String county) {
        this.county = county;
        return this;
    }

    public Address setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }
}