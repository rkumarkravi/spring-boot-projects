package com.rk.hrm.absence.mappers;

import com.rk.hrm.absence.dtos.LeaveDto;
import com.rk.hrm.absence.models.Leave;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LeaveMapper {
    Leave leaveDtoToLeave(LeaveDto leaveDto);

    LeaveDto leaveToLeaveDto(Leave leave);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLeaveFromLeaveDto(LeaveDto leaveDto, @MappingTarget Leave leave);
}
