package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.exceptions.OtpException;
import azcompany.final_projeckt.service.MailService;
import azcompany.final_projeckt.service.OtpService;
import azcompany.final_projeckt.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private final MailService mailService;
    private static  final long OTP_TTL_MINUTES = 5;


    @Override
    public String generateOtpCode(String username, String email) {
        log.info("ActionLog.generateOtp.start: username={}" , username);
        var otp = OtpUtil.generateOtp();
        var data = redisTemplate.opsForValue().get(username);
        if (data != null) {
            log.warn("actionLog.generateOtp.warn: OTP already exists for username={}", username);
            return data;
        }
        redisTemplate.opsForValue().set(username, otp,OTP_TTL_MINUTES, TimeUnit.MINUTES);
        mailService.sendEmail(email,"FINAL_PROJECT", "Your OTP is: " + otp);
        log.info("ActionLog.generateOtp.end: username={}", username);
        return otp;
    }

    @Override
    public void verifyOtp(String username, String otp) {
        log.info("ActionLog.verifyOtp.start: username={}", username);
        var storedOtp = redisTemplate.opsForValue().get(username);
        if (storedOtp == null) {
            throw new OtpException("OTP does not exist");
        } else if (!storedOtp.equals(otp)) {
            throw new OtpException("Invalid OTP");
        }
        redisTemplate.delete(username);
        log.info("ActionLog.verifyOtp.success: username={}", username);
    }
}
