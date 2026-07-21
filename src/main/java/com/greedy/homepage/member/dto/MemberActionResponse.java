package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;

public record MemberActionResponse(
        MemberRole memberRole,
        StackPosition stackPosition,
        Integer generationNumber
) {
    public static MemberActionResponse from(MemberAction memberAction) {
        return new MemberActionResponse(
                memberAction.getMemberRole(),
                memberAction.getStackPosition(),
                memberAction.getGeneration() != null ? memberAction.getGeneration().getNumber() : null
        );
    }
}
