package net.programmer.igoodie.util;

import java.util.regex.Pattern;

public class CommonPatterns {

    public static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Pattern JWT_PATTERN = Pattern.compile("^[A-Z0-9-_=]+\\.[A-Z0-9-_=]+\\.?[A-Z0-9-_.+/=]*$", Pattern.CASE_INSENSITIVE);

}
