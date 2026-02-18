package azcompany.final_projeckt.service;

import azcompany.final_projeckt.dto.user.UserRegistrationRequestDto;
import azcompany.final_projeckt.dto.user.UserResponseDto;
import azcompany.final_projeckt.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
    void verifyAccount(String email, String otp);
}
