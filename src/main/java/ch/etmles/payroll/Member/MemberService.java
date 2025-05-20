package ch.etmles.payroll.Member;

import ch.etmles.payroll.Lot.Lot;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public boolean lotIsOwnByCurrentMember(Lot lot) {
        Member currentMember = getCurrentMember();
        return lot.getOwner().getId().equals(currentMember.getId());
    }
}
