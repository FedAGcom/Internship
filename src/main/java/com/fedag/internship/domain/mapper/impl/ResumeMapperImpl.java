package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.ResumeRequest;
import com.fedag.internship.domain.dto.request.ResumeRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminResumeResponse;
import com.fedag.internship.domain.dto.response.user.ResumeResponse;
import com.fedag.internship.domain.entity.ResumeEntity;
import com.fedag.internship.domain.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ResumeMapperImpl implements ResumeMapper {
    private final ObjectMapper objectMapper;

    @Override
    public ResumeResponse toResponse(ResumeEntity resumeEntity) {
        return new ResumeResponse()
                .setId(resumeEntity.getId())
                .setLocation(resumeEntity.getLocation())
                .setPhone(resumeEntity.getPhone())
                .setResumeFile(encodeFile(resumeEntity.getResumeFile()))
                .setResumeFileType(resumeEntity.getResumeFileType())
                .setCoverLetter(resumeEntity.getCoverLetter())
                .setUserId(resumeEntity.getUser().getId());
    }

    @Override
    public AdminResumeResponse toAdminResponse(ResumeEntity resumeEntity) {
        return new AdminResumeResponse()
                .setId(resumeEntity.getId())
                .setLocation(resumeEntity.getLocation())
                .setPhone(resumeEntity.getPhone())
                .setResumeFile(encodeFile(resumeEntity.getResumeFile()))
                .setResumeFileType(resumeEntity.getResumeFileType())
                .setCoverLetter(resumeEntity.getCoverLetter())
                .setUserId(resumeEntity.getUser().getId());
    }

    private String encodeFile(byte[] file) {
        String encodedFileString = null;
        byte[] encodedFile = Base64.getEncoder().encode(file);
        try {
            encodedFileString = new String(encodedFile, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedFileString;
    }

    @Override
    public ResumeEntity fromRequest(ResumeRequest resumeRequest) {
        return objectMapper.convertValue(resumeRequest, ResumeEntity.class);
    }

    @Override
    public ResumeEntity fromRequestUpdate(ResumeRequestUpdate resumeRequestUpdate) {
        return objectMapper.convertValue(resumeRequestUpdate, ResumeEntity.class);
    }

    @Override
    public ResumeEntity merge(ResumeEntity source, ResumeEntity target) {
        if (source.getLocation() != null) {
            target.setLocation(source.getLocation());
        }
        if (source.getPhone() != null) {
            target.setPhone(source.getPhone());
        }
        if (source.getCoverLetter() != null) {
            target.setCoverLetter(source.getCoverLetter());
        }
        if (source.getResumeFile() != null) {
            target.setResumeFile(source.getResumeFile());
        }
        if (source.getResumeFileType() != null) {
            target.setResumeFileType(source.getResumeFileType());
        }
        return target;
    }
}
