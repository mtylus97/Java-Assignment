package com.assignment.project.devices.topology;

import com.assignment.project.devices.entity.DeviceEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Topology {

    private Topology() {
    }

    public static List<DeviceTopologyNode> buildTopologyTree(List<DeviceEntity> devices) {
        Map<String, DeviceTopologyNode> nodeMap = new HashMap<>();
        List<DeviceTopologyNode> roots = new ArrayList<>();
        for (DeviceEntity device : devices) {
            nodeMap.putIfAbsent(device.getMacAddress(), new DeviceTopologyNode(device.getMacAddress()));
        }
        for (DeviceEntity device : devices) {
            DeviceTopologyNode node = nodeMap.get(device.getMacAddress());
            if (device.getUplinkMacAddress() == null || device.getUplinkMacAddress().isEmpty()) {
                roots.add(node);
            } else {
                DeviceTopologyNode parent = nodeMap.get(device.getUplinkMacAddress());
                if (parent != null) {
                    parent.addChildNode(node);
                } else {
                    roots.add(node);
                }
            }
        }
        return roots;
    }

    public static DeviceTopologyNode buildTopologyTreeFromRoot(List<DeviceEntity> devices, String rootMacAddress) {
        Map<String, DeviceTopologyNode> nodeMap = new HashMap<>();
        for (DeviceEntity device : devices) {
            nodeMap.putIfAbsent(device.getMacAddress(), new DeviceTopologyNode(device.getMacAddress()));
        }
        DeviceTopologyNode root = nodeMap.get(rootMacAddress);
        if (root == null) {
            return null;
        }
        for (DeviceEntity device : devices) {
            DeviceTopologyNode node = nodeMap.get(device.getMacAddress());
            if (device.getUplinkMacAddress() != null && !device.getUplinkMacAddress().equals(rootMacAddress)) {
                DeviceTopologyNode parent = nodeMap.get(device.getUplinkMacAddress());
                if (parent != null) {
                    parent.addChildNode(node);
                }
            } else if (device.getUplinkMacAddress() != null && device.getUplinkMacAddress().equals(rootMacAddress)) {
                root.addChildNode(node);
            }
        }
        return root;
    }

}
