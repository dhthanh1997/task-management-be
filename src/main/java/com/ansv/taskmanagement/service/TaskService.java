package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {

    TaskDTO findById(Long id);

    TaskDTO save(TaskDTO item);

    TaskDTO markCompleteTask(Long id);

    List<TaskDTO> saveListTask(List<TaskDTO> listData);

    List<TaskDTO> findAll();

    List<TaskDTO> search(Map<String, Object> mapParam);

    List<TaskDTO> findByParentId(Long id);

    Page<TaskDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

    void importTask(List<TaskImportDTO> dto);
    
    File exportReport(Long projectId);



}