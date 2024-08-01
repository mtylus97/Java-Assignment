package com.assignment.project.devices.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GatewayEntity extends DeviceEntity {

    @Column(name = "NETWORK")
    String network;

}
