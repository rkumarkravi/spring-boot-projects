package com.rk.hrm.absence.models;

import com.rk.hrm.absence.enums.AbsenceType;
import com.rk.hrm.absence.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AbsenceType absenceType;
    private String leaveReason;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    private String comments;
    private Status status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_applied_by", nullable = false)
    private User user;

}