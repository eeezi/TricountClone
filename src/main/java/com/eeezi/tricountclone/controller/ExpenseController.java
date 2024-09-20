package com.eeezi.tricountclone.controller;

import com.eeezi.tricountclone.dto.ExpenseRequest;
import com.eeezi.tricountclone.dto.ExpenseResult;
import com.eeezi.tricountclone.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/expenses/add")
    public ResponseEntity<ExpenseResult> addExpenseToSettlement(
            @Valid @RequestBody ExpenseRequest expenseRequest
    ){
        return new ResponseEntity<>(expenseService.addExpense(expenseRequest), HttpStatus.OK);
    }
}
