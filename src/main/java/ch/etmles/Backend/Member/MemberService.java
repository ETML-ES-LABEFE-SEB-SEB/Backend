package ch.etmles.Backend.Member;

import ch.etmles.Backend.Lot.Lot;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public boolean memberHasAvailableWallet(BigDecimal requiredAmount) {
        Member currentMember = getCurrentMember();
        if(requiredAmount.compareTo(currentMember.getAvailableWallet()) <= 0)
            return true;
        return false;
    }

    public boolean lotIsOwnByCurrentMember(Lot lot) {
        Member currentMember = getCurrentMember();
        return lot.getOwner().getId().equals(currentMember.getId());
    }

    public boolean moveToReservedWallet(BigDecimal amount) {
        Member currentMember = getCurrentMember();
        if(currentMember.getAvailableWallet().compareTo(amount) > 0) {
            currentMember.setAvailableWallet(currentMember.getAvailableWallet().subtract(amount));
            currentMember.setReservedWallet(currentMember.getReservedWallet().add(amount));
            memberRepository.save(currentMember);
            return true;
        }
        return false;
    }

    public boolean moveToAvailableWallet(BigDecimal amount) {
        Member currentMember = getCurrentMember();
        if(currentMember.getReservedWallet().compareTo(amount) > 0) {
            currentMember.setAvailableWallet(currentMember.getAvailableWallet().add(amount));
            currentMember.setReservedWallet(currentMember.getReservedWallet().subtract(amount));
            memberRepository.save(currentMember);
            return true;
        }
        return false;
    }
}
