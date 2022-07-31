package com.rk.animestream.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_plans")
@NoArgsConstructor
@Data
public class UserPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "upid", nullable = false)
    private Long upId;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "plan_pid", nullable = false)
    private Plan plan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date addedOn=new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date validTill;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}