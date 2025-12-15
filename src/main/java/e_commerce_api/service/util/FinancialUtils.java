package e_commerce_api.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialUtils {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public static BigDecimal ensureScaleAndNonNull(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);
        }
        return value.setScale(SCALE, ROUNDING_MODE);
    }
}
