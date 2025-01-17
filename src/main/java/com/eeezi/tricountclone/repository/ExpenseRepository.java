package com.eeezi.tricountclone.repository;

import com.eeezi.tricountclone.dto.ExpenseResult;
import com.eeezi.tricountclone.model.Expense;
import com.eeezi.tricountclone.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExpenseRepository {
    private final JdbcTemplate jdbcTemplate;
    public Expense save(Expense expense) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("expense").usingGeneratedKeyColumns("id");

        Map<String, Object> parmas = new HashMap<>();
        parmas.put("name", expense.getName());
        parmas.put("settlement_id", expense.getSettlementId());
        parmas.put("payer_member_id", expense.getPayerMemberId());
        parmas.put("amount", expense.getAmount());
        parmas.put("expense_date_time", expense.getExpenseDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parmas));
        expense.setId(key.longValue());

        return expense;
    }

    public List<ExpenseResult> findExpensesWithMemberBySettlementId(Long settlementId) {
        String sql = "SELECT * " +
                "FROM settlement_participant " +
                "JOIN member ON settlement_participant.member_id = member.id " +
                "LEFT JOIN expense ON settlement_participant.member_id = expense.payer_member_id " +
                "AND settlement_participant.settlement_id = expense.settlement_id " +
                "WHERE settlement_participant.settlement_id = ?";
        return jdbcTemplate.query(sql, expenseResultRowMapper(), settlementId);
    }
    private RowMapper<ExpenseResult> expenseResultRowMapper() {
        return (rs, rowNum) -> {
            ExpenseResult expenseResult = new ExpenseResult();
            expenseResult.setSettlementId(rs.getLong("settlement_participant.settlement_id"));
            BigDecimal amt = rs.getBigDecimal("expense.amount");
            expenseResult.setAmount(amt != null ? amt : BigDecimal.ZERO);

            Member member = new Member();
            if(rs.getLong("member.id") != 0) {
                member.setId(rs.getLong("member.id"));
                member.setLoginId(rs.getString("member.login_id"));
                member.setPassword(rs.getString("member.password"));
                member.setName(rs.getString("member.name"));

                expenseResult.setPayerMember(member);
            }

            return expenseResult;
        };
    }


}
