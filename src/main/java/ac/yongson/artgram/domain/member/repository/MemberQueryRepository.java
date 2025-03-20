package ac.yongson.artgram.domain.member.repository;

import ac.yongson.artgram.domain.member.dto.MemberResponseDTO;
import ac.yongson.artgram.domain.member.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member = QMember.member;

    public MemberResponseDTO.MemberSimpleInfo getMemberSimpleInfo(Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(MemberResponseDTO.MemberSimpleInfo.class,
                        member.studentId,
                        member.memberName,
                        member.image.imageUrl.as("profileImageUrl")))
                .from(member)
                .where(member.memberId.eq(memberId))
                .fetchFirst();
    }
}
