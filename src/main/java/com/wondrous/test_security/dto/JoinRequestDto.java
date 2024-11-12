package com.wondrous.test_security.dto;

import com.wondrous.test_security.enrity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class JoinRequestDto {
    private String memberName;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .build();
    }
}
