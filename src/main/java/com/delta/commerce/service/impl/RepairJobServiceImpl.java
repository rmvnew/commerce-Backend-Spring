package com.delta.commerce.service.impl;

import com.delta.commerce.repository.RepairJobRepository;
import com.delta.commerce.service.RepairJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairJobServiceImpl implements RepairJobService {

    @Autowired
    private RepairJobRepository repairJobRepository;


}
