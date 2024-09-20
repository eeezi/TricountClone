package com.eeezi.tricountclone.service;

import com.eeezi.tricountclone.dto.ExpenseRequest;
import com.eeezi.tricountclone.dto.ExpenseResult;
import com.eeezi.tricountclone.model.Expense;
import com.eeezi.tricountclone.model.Member;
import com.eeezi.tricountclone.model.Settlement;
import com.eeezi.tricountclone.repository.ExpenseRepository;
import com.eeezi.tricountclone.repository.MemberRepository;
import com.eeezi.tricountclone.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final SettlementRepository settlementRepository;

    @Transactional
    public ExpenseResult addExpense(ExpenseRequest expenseRequest) {
        // 예외 처리
        Optional<Member> payer = memberRepository.findById(expenseRequest.getPayerMemberId());
        if(!payer.isPresent()) {
            throw new RuntimeException("INVALID MEMBER ID! (Payer)");
        }

        Optional<Settlement> settlement = settlementRepository.findById(expenseRequest.getSettlementId());
        if(!settlement.isPresent()) {
            throw new RuntimeException("INVALID SETTLEMNET ID");
        }

        Expense expense = Expense.builder()
                .name(expenseRequest.getName())
                .settlementId(expenseRequest.getSettlementId())
                .payerMemberId(expenseRequest.getPayerMemberId())
                .amount(expenseRequest.getAmount())
                .expenseDateTime(Objects.nonNull(expenseRequest.getExpenseDateTime()) ? expenseRequest.getExpenseDateTime() : LocalDateTime.now() )
                .build();

        expenseRepository.save(expense);

        return new ExpenseResult(expense, payer.get());

    }

}
