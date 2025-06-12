package com.chucheka.orderservice.entities;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
        @AttributeOverride(name = "email", column = @Column(name = "customer_email")),

})
public class CustomerDetails {

    private String name;
    private String email;
    private String phoneNumber;

    private String address;
    private String city;
    private String state;
    private String zipCode;
}