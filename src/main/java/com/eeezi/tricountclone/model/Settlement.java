package com.eeezi.tricountclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {
    private Long id;
    private String name;
    // 특정 정산에 참여한 유저만 정산 내역 열람 가능
    private List<Member> participants = Collections.emptyList();
}
