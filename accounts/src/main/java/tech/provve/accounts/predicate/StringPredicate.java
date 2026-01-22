package tech.provve.accounts.predicate;

public class StringPredicate {

    /**
     * Возвращает true, если аргумент является пустой строкой ("") или null.
     *
     * @param string строка для проверки
     * @return true, если строка пустая или равна null
     */
    public static boolean isBlank(final String string) {
        return string == null || string.trim()
                                       .isEmpty();
    }

    /**
     * Возвращает true, если аргумент не является пустой строкой ("") и не равен null.
     *
     * @param string строка для проверки
     * @return true, если строка непустая и не null
     */
    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    private StringPredicate() {
    }

}
