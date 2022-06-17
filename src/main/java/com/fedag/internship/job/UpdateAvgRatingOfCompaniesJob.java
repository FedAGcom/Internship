package com.fedag.internship.job;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAvgRatingOfCompaniesJob {
    private final CommentRepository commentRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @Scheduled(fixedRateString = "${job.timeForScheduler}")
    public void updateAvgRatingForCompanies(){
        log.info("Начало обновления рейтинга компаний");
        List<CompanyEntity> list = companyRepository.findAll();
        list.forEach((element) ->
                        element.setRating(commentRepository.getAvgRatingOfCompany(element.getId())));
        log.info("Рейтинг компаний обновлен");
    }
}
