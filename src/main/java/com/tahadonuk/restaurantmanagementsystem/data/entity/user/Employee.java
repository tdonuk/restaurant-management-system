package com.tahadonuk.restaurantmanagementsystem.data.entity.employee;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.PhoneNumber;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private long employeeId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "firstName", column = @Column(name = "FIRST_NAME")),
            @AttributeOverride( name = "lastName", column = @Column(name = "LAST_NAME"))
    })
    private Name name;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @ElementCollection
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @ElementCollection
    private List<Address> addressList = new ArrayList<>();

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
