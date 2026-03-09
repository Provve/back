package tech.provve.skill.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * For removing XSS scripts
 */
public class XssSanitizer {

    public static String sanitize(String s) {
        return Jsoup.clean(s, Safelist.none());
    }

}
