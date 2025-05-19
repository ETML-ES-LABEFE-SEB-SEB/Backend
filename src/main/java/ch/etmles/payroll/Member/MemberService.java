package ch.etmles.payroll.Member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getCurrentMember() {
        // TODO : This is for testing only !
        return memberRepository.findByUsername("Tartempion");
    }
}
