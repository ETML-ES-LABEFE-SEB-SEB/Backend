package ch.etmles.Backend.Member;

import ch.etmles.Backend.Lot.Lot;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Member getCurrentMember() {
        // TODO : This is for testing only !
        return memberRepository.findByUsername("Tartempion").get();
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
