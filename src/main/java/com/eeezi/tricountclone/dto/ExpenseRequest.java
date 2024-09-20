package com.eeezi.tricountclone.dto;

import com.eeezi.tricountclone.util.MemberContext;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseRequest {
    @NotNull
    private String name;
    @NotNull
    private Long settlementId;
    private Long payerMemberId = MemberContext.getCurrentMember().getId();
    @NotNull
    private BigDecimal amount;
    private LocalDateTime expenseDateTime;
}
