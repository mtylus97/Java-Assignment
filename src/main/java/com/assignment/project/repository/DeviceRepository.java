package com.assignment.project.repository;

import com.assignment.project.devices.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {

    List<DeviceEntity> findAll();

    DeviceEntity findDeviceByMacAddress(String macAddress);

}
