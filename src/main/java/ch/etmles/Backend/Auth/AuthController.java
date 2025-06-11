package ch.etmles.Backend.Auth;

import ch.etmles.Backend.Auth.DTO.AuthLoginDTO;
import ch.etmles.Backend.Auth.Exceptions.WrongCredentialsException;
import ch.etmles.Backend.Member.DTO.MemberAddDTO;
import ch.etmles.Backend.Member.Exceptions.MemberAlreadyExistsException;
import ch.etmles.Backend.Member.Member;
import ch.etmles.Backend.Member.MemberRepository;
import ch.etmles.Backend.Member.MemberRole;
import ch.etmles.Backend.ResponseAPI.SingleApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;

import static ch.etmles.Backend.Data.apiVersion.API_VERSION;

@RequestMapping("/" + API_VERSION + "/auth")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public SingleApiResponse<AuthLoginDTO> login(@RequestBody MemberAddDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            Member member = (Member) authentication.getPrincipal();
            String token = jwtUtil.generateToken(member);

            AuthLoginDTO authLoginDTO = new AuthLoginDTO();
            authLoginDTO.setToken(token);

            return new SingleApiResponse<AuthLoginDTO>(authLoginDTO);
        } catch (AuthenticationException e) {
            throw new WrongCredentialsException();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberAddDTO newMember) {
        Member memberToCreate = new Member();
        memberToCreate.setUsername(newMember.getUsername());

        if(memberRepository.findByUsername(newMember.getUsername()).isPresent()) {
            throw new MemberAlreadyExistsException(newMember.getUsername());
        }
        memberToCreate.setPasswordHash(passwordEncoder.encode(newMember.getPassword()));
        memberToCreate.setRole(MemberRole.USER);
        memberToCreate.setAvailableWallet(new BigDecimal(0));
        memberToCreate.setReservedWallet(new BigDecimal(0));
        memberToCreate.setProfilePicture("https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/no_picture.png");
        memberToCreate.setBids(new ArrayList<>());
        memberRepository.save(memberToCreate);
        return ResponseEntity.ok("User registered");
    }
}
