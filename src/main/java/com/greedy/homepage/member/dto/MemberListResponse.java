package com.greedy.homepage.member.dto;

import com.greedy.homepage.member.domain.BaseMember;
import com.greedy.homepage.member.domain.Member;
import com.greedy.homepage.member.domain.MemberAction;
import com.greedy.homepage.member.domain.enums.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record MemberListResponse(
        @Schema(description = "멤버 ID", example = "1")
        Long id,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "GitHub URL", example = "https://github.com/hong", nullable = true)
        String githubUrl,

        @Schema(description = "소속 부서 한국어 이름 목록")
        List<String> departmentKoreanNames,

        @Schema(description = "멤버 활동 이력")
        List<MemberActionResponse> memberActions
) {
    public static MemberListResponse of(BaseMember member, List<MemberAction> memberActions) {
        List<Department> departments = member instanceof Member m ? m.getDepartments() : List.of();

        return new MemberListResponse(
                member.getId(),
                member.getName(),
                member.getGithubUrl(),
                departments.stream().map(Department::getKoreanName).toList(),
                memberActions.stream().map(MemberActionResponse::from).toList()
        );
    }
}
