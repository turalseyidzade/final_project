package azcompany.final_projeckt.config;

import azcompany.final_projeckt.dto.user.UserLoginRequestDto;
import azcompany.final_projeckt.dto.user.UserLoginResponseDto;
import azcompany.final_projeckt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTUtil jwtUtil;
    private final AuthenticationProvider authenticationProvider;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );

        String generatedToken = jwtUtil.generateAccessToken(authentication.getName());
        return new UserLoginResponseDto(generatedToken);
    }
}
