package com.assignment.project.devices.mapper;

import com.assignment.project.devices.dto.AccessPoint;
import com.assignment.project.devices.dto.Gateway;
import com.assignment.project.devices.dto.Switch;
import com.assignment.project.devices.entity.AccessPointEntity;
import com.assignment.project.devices.entity.GatewayEntity;
import com.assignment.project.devices.entity.SwitchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    AccessPointEntity fromDto(AccessPoint dto);

    GatewayEntity fromDto(Gateway dto);

    SwitchEntity fromDto(Switch dto);

    AccessPoint toDto(AccessPointEntity entity);

    Gateway toDto(GatewayEntity entity);

    Switch toDto(SwitchEntity entity);

}
