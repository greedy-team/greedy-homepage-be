package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberActionResponse(
        @Schema(description = "멤버 역할", example = "STUDY_LEAD")
        MemberRole memberRole,

        @Schema(description = "기술 스택 포지션", example = "BACKEND")
        StackPosition stackPosition,

        @Schema(description = "기수 번호 (기수가 없는 활동은 null)", example = "1", nullable = true)
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
