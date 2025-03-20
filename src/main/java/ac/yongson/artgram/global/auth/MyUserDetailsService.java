package ac.yongson.artgram.global.auth;

import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        Member memberPS = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("notFoundMember"));

        return new MyUserDetails(memberPS);
    }
}
