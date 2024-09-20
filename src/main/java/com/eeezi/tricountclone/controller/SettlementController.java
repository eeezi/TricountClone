package com.eeezi.tricountclone.controller;

import com.eeezi.tricountclone.dto.BalanceResult;
import com.eeezi.tricountclone.model.Settlement;
import com.eeezi.tricountclone.util.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @PostMapping("/settles/create")
    public ResponseEntity<Settlement> createSettlement(@RequestParam String settlementName) {
        return new ResponseEntity<>(settlementService.createAndJoinSettlement(settlementName, MemberContext.getCurrentMember()), HttpStatus.OK);
    }

    @PostMapping("/settles/{id}/join")
    public ResponseEntity<Void> joinSettlement(@PathVariable("id") Long settlementId) {
        settlementService.joinSettlement(settlementId, MemberContext.getCurrentMember().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/settles/{id}/balance")
    public ResponseEntity<List<BalanceResult>> getSettlementBalanceResult(@PathVariable("id") Long settlementId) {
        return new ResponseEntity<>(settlementService.getBalanceResult(settlementId), HttpStatus.OK);
    }
}
