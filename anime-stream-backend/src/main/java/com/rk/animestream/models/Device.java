package com.rk.animestream.models;

import com.rk.animestream.enums.DeviceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "device")
@NoArgsConstructor
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "did", nullable = false)
    private Long did;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    private String deviceIdentifier;
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

}