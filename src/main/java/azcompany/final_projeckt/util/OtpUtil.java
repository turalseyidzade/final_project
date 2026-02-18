package azcompany.final_projeckt.util;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp() {
        return new Random().nextInt(100_000, 999_999) + "";
    }
}
