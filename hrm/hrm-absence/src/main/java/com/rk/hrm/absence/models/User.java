package com.rk.hrm.absence.models;

import com.rk.hrm.absence.enums.GENDER;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    @Email(message = "Enter Valid Email id")
    private String email;
    @Column(nullable = false, unique = true)
    @Size(min = 10)
    private String phone;
    private String address;
    private String role;
    private String status;
    private String designation;
    @Temporal(TemporalType.DATE)
    private Date joiningDate;
    @Temporal(TemporalType.DATE)
    @Past
    private Date dob;

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at=new Date();

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne
    @JoinColumn(name = "reporting_to_id")
    private User reportingTo;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public User(String username, String password, String fullName, String email, String phone, String address, String role, String status, String designation,Date dob,GENDER gender) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = status;
        this.designation = designation;
        this.dob= Optional.of(dob).orElse(new Date());
        this.gender=gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", designation='" + designation + '\'' +
                ", department=" + department +
                ", reportingToId=" + reportingTo +
                ", project=" + project +
                ", gender=" + gender +
                '}';
    }
}