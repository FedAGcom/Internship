package com.fedag.internship.config;

import com.fedag.internship.domain.entity.CompanyELSEntity;
import com.fedag.internship.repository.ELSCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SessionImplementor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Component
public class HibernateSearchConfig implements ApplicationListener<ContextRefreshedEvent> {
    private final EntityManager entityManager;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        CompanyELSEntity elsEntity = new CompanyELSEntity("zalupa");
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager.unwrap(SessionImplementor.class));
//        try {
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            throw new RuntimeException("FullTextEntityManager can't create indexes for searchable entities");
//        }
    }
}
