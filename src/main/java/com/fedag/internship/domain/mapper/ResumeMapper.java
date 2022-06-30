package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.ResumeRequest;
import com.fedag.internship.domain.dto.request.ResumeRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminResumeResponse;
import com.fedag.internship.domain.dto.response.user.ResumeResponse;
import com.fedag.internship.domain.entity.ResumeEntity;

public interface ResumeMapper {
    ResumeResponse toResponse(ResumeEntity resumeEntity);

    AdminResumeResponse toAdminResponse(ResumeEntity resumeEntity);

    ResumeEntity fromRequest(ResumeRequest resumeRequest);

    ResumeEntity fromRequestUpdate(ResumeRequestUpdate resumeRequestUpdate);

    ResumeEntity merge(ResumeEntity source, ResumeEntity target);
}
