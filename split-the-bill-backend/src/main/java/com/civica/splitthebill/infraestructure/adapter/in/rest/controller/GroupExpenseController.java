package com.civica.splitthebill.infraestructure.adapter.in.rest.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.domain.port.in.ExpensePortIn;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseRequest;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseRequestResponseMapper;
import com.civica.splitthebill.infraestructure.adapter.in.rest.dto.ExpenseResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class GroupExpenseController {

    private static final String ID_NOT_NULL = "Id cannot be null";

    private final ExpensePortIn expenseService;

    public GroupExpenseController(ExpensePortIn expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroupId(@PathVariable Long groupId) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        List<ExpenseDTO> expenses = expenseService.listExpensesInGroupUseCase(groupId);
        List<ExpenseResponse> response = expenses.stream()
                .map(ExpenseRequestResponseMapper::domainDTOToResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpenseByGroupId(@PathVariable Long groupId,
            @Valid @RequestBody ExpenseRequest request) {
        Objects.requireNonNull(groupId, ID_NOT_NULL);

        ExpenseDTO input = ExpenseRequestResponseMapper.requestToDomainDTO(request);
        ExpenseDTO created = expenseService.createExpenseUseCase(input);

        ExpenseResponse response = ExpenseRequestResponseMapper.domainDTOToResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
