package aldentebackend.constants;

import java.time.LocalDate;

public class ReportConstants {
    public static LocalDate START = LocalDate.of(2022, 1, 1);
    public static LocalDate END = LocalDate.of(2022, 1, 28);

    public static LocalDate START_EMPTY = LocalDate.of(2021, 9, 10);
    public static LocalDate END_EMPTY = LocalDate.of(2021, 10, 10);

    public static LocalDate START_LESS = LocalDate.of(2021, 10, 15);
    public static LocalDate END_LESS = LocalDate.of(2021, 10, 1);

    public static LocalDate START_INVALID = LocalDate.of(2022, 11, 11);
    public static LocalDate END_INVALID = LocalDate.of(2022, 12, 11);


    public static Long ITEM_10015L = 10015L;
    public static Double ITEM_10015L_INCOME = 660.0;
    public static Double ITEM_10015L_EXPENSE = 530.0;

    public static Long ITEM_10023L = 10023L;
    public static Double ITEM_10023L_INCOME = 350.0;
    public static Double ITEM_10023L_EXPENSE = 200.0;
}
