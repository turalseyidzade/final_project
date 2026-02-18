package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.dao.repositories.UserRepository;
import azcompany.final_projeckt.dto.user.UserRegistrationRequestDto;
import azcompany.final_projeckt.dto.user.UserResponseDto;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.exceptions.RegistrationException;
import azcompany.final_projeckt.mapper.UserMapper;
import azcompany.final_projeckt.dao.entities.User;
import azcompany.final_projeckt.service.OtpService;
import azcompany.final_projeckt.service.ShoppingCartService;
import azcompany.final_projeckt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;
    private final OtpService otpService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException {
         if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
             throw new RegistrationException(String.format("Can't register user with email %s", requestDto.getEmail()));
         }

         User user = userMapper.toUser(requestDto);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setEnabled(false);

         User savedUser = userRepository.save(user);
         shoppingCartService.createShoppingCart(savedUser);
         otpService.generateOtpCode(savedUser.getEmail(), savedUser.getEmail());

         log.info("ActionLog.register.success: User created and OTP sent to {}", savedUser.getEmail());
         return userMapper.toDto(savedUser);
    }


    @Override
    @Transactional
    public void verifyAccount(String email, String otp) {
        log.info("ActionLog.verifyAccount.start: email={}", email);
        otpService.verifyOtp(email, otp);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        user.setEnabled(true);

        log.info("ActionLog.verifyAccount.success: user {} is now enabled\", email");

    }

}
