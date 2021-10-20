package com.tahadonuk.restaurantmanagementsystem.data.entity.user;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customers")
@Data
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private long customerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name = "CUSTOMER_FIRST_NAME")),
            @AttributeOverride( name = "lastName", column = @Column(name = "CUSTOMER_LAST_NAME"))
    })
    private Name name;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "email", column = @Column(name = "CUSTOMER_EMAIL"))
    })
    private Email email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
}
