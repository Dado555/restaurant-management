package aldentebackend.constants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class SalaryConstants {

    public static Long USER_ID = 1L;
    public static Long EPOCH = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    public static Double VALUE = 100000.0;
    public static Long DATE = 1644513779000L;

    public static final long BIG = 99219849818L;
    public static final long SMALL = -6565L;

    public static Long VALID_SALARY = 10052L;
    public static Double VALID_VALUE = 30000.0;
    public static Long VALID_USER_ID = 10008L;

    public static Long VALID_SALARY_AT = 10051L;
    public static Double VALID_VALUE_AT = 20000.0;
    public static Long VALID_DATE_AT = 1637297604L;

    public static Long VALID_EPOCH = 1637227789L;
    public static final long INVALID_EPOCH_SMALL = -127817061000L;
    public static final long INVALID_EPOCH_BIG = 1923405339000L;
    public static Long PRICE_ID_EPOCH = 10054L;

    public static Long PRICE_ID_2ND = 10055L;

}
