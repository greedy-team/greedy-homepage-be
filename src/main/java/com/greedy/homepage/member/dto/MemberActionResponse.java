package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.StackPosition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(oneOf = {InternalMemberActionResponse.class, ExternalMemberActionResponse.class})
public sealed interface MemberActionResponse
        permits InternalMemberActionResponse, ExternalMemberActionResponse {

    enum Type {
        INTERNAL, EXTERNAL
    }

    Type type();

    StackPosition stackPosition();

    Integer generationNumber();

    static MemberActionResponse from(MemberAction memberAction) {
        if (memberAction.getExternalMemberRole() != null) {
            return ExternalMemberActionResponse.from(memberAction);
        }
        return InternalMemberActionResponse.from(memberAction);
    }
}
