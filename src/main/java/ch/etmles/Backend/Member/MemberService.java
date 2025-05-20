package ch.etmles.Backend.Member;

import ch.etmles.Backend.Lot.Lot;
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

    public boolean lotIsOwnByCurrentMember(Lot lot) {
        Member currentMember = getCurrentMember();
        return lot.getOwner().getId().equals(currentMember.getId());
    }
}
