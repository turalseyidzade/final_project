package azcompany.final_projeckt.controller;

import azcompany.final_projeckt.config.AuthenticationService;
import azcompany.final_projeckt.dto.user.UserLoginRequestDto;
import azcompany.final_projeckt.dto.user.UserLoginResponseDto;
import azcompany.final_projeckt.dto.user.UserRegistrationRequestDto;
import azcompany.final_projeckt.dto.user.UserResponseDto;
import azcompany.final_projeckt.exceptions.RegistrationException;
import azcompany.final_projeckt.service.UserService;
import azcompany.final_projeckt.util.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;

    @PostMapping(value = "/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping(value = "/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
    @PostMapping("/verify")
    public UserLoginResponseDto verify(@RequestParam String email, @RequestParam String otp) {
        userService.verifyAccount(email, otp);

        // Token yarat
        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        return new UserLoginResponseDto(accessToken, refreshToken);
    }
}
