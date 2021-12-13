package com.tahadonuk.restaurantmanagementsystem.data.entity.user;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long userId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name = "FIRST_NAME")),
            @AttributeOverride( name = "lastName", column = @Column(name = "LAST_NAME"))
    })
    private Name name;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "STREET")),
            @AttributeOverride( name = "apartment", column = @Column(name = "APARTMENT"))
    })
    private Address address;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "SALARY")
    private double salary;

    @Column(name = "JOIN_DATE")
    private Date joinDate;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "LAST_LOGIN")
    private Date lastLoginDate;

    @Column(name = "LAST_LOGOUT")
    private Date lastLogoutDate;

    @Column(name = "PASSWORD")
    private String password;

}
