package azcompany.final_projeckt.service;

public interface OtpService {
    String generateOtpCode(String username, String email);
    void verifyOtp(String username, String otp);
}
