package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.ExternalMemberRole;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberActionResponse(
        @Schema(description = "멤버 역할 (내부 멤버)", example = "STUDY_LEAD", nullable = true)
        MemberRole memberRole,

        @Schema(description = "외부 멤버 역할 (외부 멤버)", example = "REVIEWER", nullable = true)
        ExternalMemberRole externalMemberRole,

        @Schema(description = "기술 스택 포지션", example = "BACKEND")
        StackPosition stackPosition,

        @Schema(description = "기수 번호 (기수가 없는 활동은 null)", example = "1", nullable = true)
        Integer generationNumber
) {
    public static MemberActionResponse from(MemberAction memberAction) {
        return new MemberActionResponse(
                memberAction.getMemberRole(),
                memberAction.getExternalMemberRole(),
                memberAction.getStackPosition(),
                memberAction.getGeneration() != null ? memberAction.getGeneration().getNumber() : null
        );
    }
}
