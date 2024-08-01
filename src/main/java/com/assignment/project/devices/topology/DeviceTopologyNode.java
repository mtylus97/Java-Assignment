package com.assignment.project.devices.topology;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DeviceTopologyNode {

    private final String macAddress;
    private final List<DeviceTopologyNode> childNodes = new ArrayList<>();

    public DeviceTopologyNode(String deviceMacAddress) {
        this.macAddress = deviceMacAddress;
    }

    public void addChildNode(DeviceTopologyNode childNode)  {
        this.childNodes.add(childNode);
    }

}
