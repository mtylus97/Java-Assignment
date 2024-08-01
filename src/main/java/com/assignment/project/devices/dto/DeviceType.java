package com.assignment.project.devices.dto;

import lombok.Getter;

@Getter
public enum DeviceType {

    GATEWAY(1),

    SWITCH(2),

    ACCESS_POINT(3);

    //sorting order: Gateway > Switch > Access Point
    public final int order;

    DeviceType(int order) {
        this.order = order;
    }

}
