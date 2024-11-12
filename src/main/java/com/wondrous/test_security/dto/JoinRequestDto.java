package com.wondrous.test_security.dto;

import com.wondrous.test_security.enrity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private String memberName;
    private String passWord;

    public Member toEntity() {
        return Member.builder()
                .userName(memberName)
                .build();
    }
}
