package com.rk.hrm.models;

import com.rk.hrm.enums.Status;
import com.rk.hrm.enums.TaskTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String taskTitle;
    private String taskDesc;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private TaskTypes taskTypes;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "assigned_for", nullable = false, updatable = false)
    private User assignedFor;
    @ManyToOne
    @JoinColumn(name = "assigned_to", updatable = false)
    private User assignedTo;
}