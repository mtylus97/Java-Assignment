package com.assignment.project.devices.entity;

import com.assignment.project.devices.dto.DeviceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEVICES")
@Getter
@Setter
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MAC_ADDRESS", length = 17, nullable = false, unique = true)
    private String macAddress;

    @Column(name = "UPLINK_MAC_ADDRESS", length = 17)
    private String uplinkMacAddress;

    @Column(name = "DEVICE_TYPE")
    private DeviceType deviceType;

}
