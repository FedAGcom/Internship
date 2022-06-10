package com.fedag.internship.job;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.repository.CompanyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UpdateAvgRatingOfCompaniesJob {

    private final CommentRepository commentRepository;
    private final CompanyRepository companyRepository;

    public UpdateAvgRatingOfCompaniesJob(CommentRepository commentRepository, CompanyRepository companyRepository) {
        this.commentRepository = commentRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void updateAvgRatingForCompanies(){
        List<CompanyEntity> list = companyRepository.findAll();
        list.forEach((element) ->
                        element.setRating(commentRepository.getAvgRatingOfCompany(element.getId())));
    }
}
