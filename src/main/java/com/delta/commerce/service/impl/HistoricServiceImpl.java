package com.delta.commerce.service.impl;

import com.delta.commerce.entity.Historic;
import com.delta.commerce.entity.User;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.repository.HistoricRepository;
import com.delta.commerce.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Service
public class HistoricServiceImpl implements HistoricService {

    @Autowired
    private HistoricRepository historicRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> void saveHistoric(Class<T> entityClass, Long entityId, User user, HistoricDescriptionEnum descriptionEnum) {

        var tableName = entityManager.getMetamodel().entity(entityClass).getJavaType().getAnnotation(Table.class).name();

        Historic historic = new Historic();
        historic.setHistoricEntity(tableName);
        historic.setHistoricEntityId(entityId);
        historic.setHistoricDescription(descriptionEnum.getDescription());
        historic.setUser(user);
        historic.setCreateAt(LocalDateTime.now());

        this.historicRepository.save(historic);

    }
}
