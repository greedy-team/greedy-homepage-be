package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.MemberRole;
import com.greedy.homepage.member.domain.enums.StackPosition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "내부 멤버 활동 응답")
public record InternalMemberActionResponse(
        @Schema(description = "멤버 역할", example = "STUDY_LEAD")
        MemberRole memberRole,

        @Schema(description = "기술 스택 포지션", example = "BACKEND")
        StackPosition stackPosition,

        @Schema(description = "기수 번호 (기수가 없는 활동은 null)", example = "1", nullable = true)
        Integer generationNumber
) implements MemberActionResponse {

    @Override
    public Type type() {
        return Type.INTERNAL;
    }

    public static InternalMemberActionResponse from(MemberAction memberAction) {
        return new InternalMemberActionResponse(
                memberAction.getMemberRole(),
                memberAction.getStackPosition(),
                memberAction.getGenerationNumber()
        );
    }
}
