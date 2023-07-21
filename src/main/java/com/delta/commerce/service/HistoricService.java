package com.delta.commerce.service;

import com.delta.commerce.entity.User;
import com.delta.commerce.enums.HistoricDescriptionEnum;

public interface HistoricService {

    <T> void saveHistoric(Class<T> entityClass, Long entityId, User user, HistoricDescriptionEnum descriptionEnum);


}
