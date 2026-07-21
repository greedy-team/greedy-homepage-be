package com.greedy.homepage.common.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SwaggerDescription {

    private static final String ENUM_PARTS = """
            <details>
            <summary><b><span style='color:#4B89DC;'>👤 MemberRole (멤버 역할)</span></b></summary>
            <div style="margin-left:10px; line-height:1.6;">
            ─────────────<br>
            • CO_FOUNDER: 공동 창립자<br>
            • MAINTAINER: 메인테이너<br>
            • STUDY_LEAD: 스터디 리드<br>
            • STUDY_MEMBER: 스터디원<br>
            • REVIEWER: 리뷰어<br>
            </div>
            </details>

            <details>
            <summary><b><span style='color:#F29661;'>🌐 ExternalMemberRole (외부 멤버 역할)</span></b></summary>
            <div style="margin-left:10px; line-height:1.6;">
            ─────────────<br>
            • REVIEWER: 리뷰어<br>
            • PROJECT_MEMBER: 프로젝트 참여자<br>
            </div>
            </details>

            <details>
            <summary><b><span style='color:#F15F5F;'>💻 StackPosition (기술 스택 포지션)</span></b></summary>
            <div style="margin-left:10px; line-height:1.6;">
            ─────────────<br>
            • BACKEND: 백엔드<br>
            • FRONTEND: 프론트엔드<br>
            • FULL_STACK: 풀스택<br>
            • DESIGN: 디자인<br>
            </div>
            </details>

            <details>
            <summary><b><span style='color:#8B00FF;'>📁 ProjectType (프로젝트 유형)</span></b></summary>
            <div style="margin-left:10px; line-height:1.6;">
            ─────────────<br>
            • FESTIVAL: 축제<br>
            • TASK_FORCE: tf팀<br>
            • GENERATION: 기수별 프로젝트<br>
            </div>
            </details>
            """;

    public String getDescription() {
        return """
                <div style="text-align:center; font-size:17px; font-weight:bold; margin-bottom:10px;">
                
                <br>
                <b>< 공통 ENUM 용어 정리 ></b>
                </div>
                
                %s
                """.formatted(ENUM_PARTS);
    }
}
