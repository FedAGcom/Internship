package com.fedag.internship.config;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.Role;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class HibernateSearchConfig implements ApplicationListener<ContextRefreshedEvent> {
    private final EntityManager entityManager;
    private final CompanyService companyService;
    private final UserService userService;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager.unwrap(SessionImplementor.class));
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException("FullTextEntityManager can't create indexes for searchable entities");
        }
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity(null, "email1@.ru", "Admin", "Ivanov",
                "$2a$12$bo/Sadk.hDcsGxARvSV/C.PCPHikdF5aVBrIS7iGCUuBtbxW/Is8y", Role.ADMIN,
                LocalDateTime.now(),null, null, null, null));
        UserEntity userEntity = new UserEntity(null, "email2@.ru", "User", "Ivanov",
                "$2a$12$jZRumcuTdvgGAaSktaatgOerh64TAXNFDq73b.KxycjntixqRKsT6", Role.USER,
                LocalDateTime.now(),null, null, null, null);
        users.add(userEntity);

        users.forEach(userService::createUser);

        List<CompanyEntity> companyEntities = new ArrayList<>();
        companyEntities.add(new CompanyEntity(null, "FedAG", "description",
                5d, "SPb", "link", userEntity, null));
        companyService.createCompany((long) 2, companyEntities.get(0));

    }
}
