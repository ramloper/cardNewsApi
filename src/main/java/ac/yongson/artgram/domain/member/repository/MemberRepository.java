package ac.yongson.artgram.domain.member.repository;

import ac.yongson.artgram.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByStudentId(String studentId);
    @Query("select m from Member m where m.studentId = :studentId and m.memberName = :memberName")
    Optional<Member>  findByStudentIdAndMemberName(@Param("studentId") String studentId, @Param("memberName") String memberName);
}
