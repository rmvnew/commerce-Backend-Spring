package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.RepairJobRequestDto;
import com.delta.commerce.dto.request.WorkOrderLineRequestDto;
import com.delta.commerce.dto.request.WorkOrderRequestDto;
import com.delta.commerce.entity.RepairJob;
import com.delta.commerce.entity.WorkOrder;
import com.delta.commerce.entity.WorkOrderLine;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.enums.WorkOrderStatus;
import com.delta.commerce.repository.RepairJobRepository;
import com.delta.commerce.repository.WorkOrderRepository;
import com.delta.commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private WorkOrderLineService workOrderLineService;

    @Autowired
    private CustomerProductService customerProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairJobRepository repairJobRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private HistoricService historicService;


    @Override
    public void createWorkOrder(WorkOrderRequestDto dto) {

        Set<RepairJob> repairJobs = new HashSet<>();


        if (!dto.getRepairJobRequestDtos().isEmpty()) {
            for (RepairJobRequestDto repairJobRequestDto : dto.getRepairJobRequestDtos()) {

                Set<WorkOrderLine> workOrderLines = new HashSet<>();

                if (!repairJobRequestDto.getWorkOrderLineRequestDto().isEmpty()) {
                    for (WorkOrderLineRequestDto lineRequestDto : repairJobRequestDto.getWorkOrderLineRequestDto()) {

                        workOrderLines.add(this.workOrderLineService.createWorkOrderLine(lineRequestDto));

                    }
                }

                var customProductSaved = this.
                        customerProductService.createCustomProduct(repairJobRequestDto.getCustomerProductRequestDto());

                var repairJob = new RepairJob();
                repairJob.setUser(this.userService.findById(repairJobRequestDto.getUser_id()));
                repairJob.setCustomerProduct(customProductSaved);
                repairJob.setPrice(repairJobRequestDto.getPrice());
                repairJob.setDescription(repairJobRequestDto.getDescription());
                repairJob.setEstimatedDuration(repairJobRequestDto.getEstimatedDuration());
                repairJob.setReportedDefect(repairJobRequestDto.getReportedDefect());
                repairJob.setWorkOrderLines(workOrderLines);

                var repairJobsSaved = this.repairJobRepository.save(repairJob);

                repairJobs.add(repairJobsSaved);


            }
        }

        var order = new WorkOrder();
        order.setRepairJobs(repairJobs);
        order.setCreateAt(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());
        order.setUser(this.userService.getLoggedInUser());
        order.setActive(true);
        order.setWorkOrderStatus(WorkOrderStatus.OPEN);
        order.setClient(this.clientService.findById(dto.getClient_id()));

        var currentOrderNumber = this.getLastOrderNumber();
        currentOrderNumber++;
        order.setWorkOrderNumber(currentOrderNumber);

        var workOrderSaved = this.workOrderRepository.save(order);

        this.historicService.saveHistoric(WorkOrder.class, workOrderSaved.getWorkOrderId(),
                this.userService.getLoggedInUser(), HistoricDescriptionEnum.WORK_ORDER_CREATE);

    }

    @Override
    public Integer getLastOrderNumber() {
        return this.workOrderRepository.getLastOrderNumber();
    }
}
