package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String args[]) {
        // String url = "http://www.netstudy5.dev/student/accountCenter.htm?t=132834";
        String url = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_1_1 like Mac OS X) AppleWebKit/602.2.14 (KHTML, like Gecko) Mobile/14B100 netstudy_6120.0_2016121417-device_14_iPhone 7";
        // Pattern p = Pattern.compile("[\\/]account(.+?).htm");
        Pattern p = Pattern.compile("-device_([0-9\\.]*)_");
        Matcher mc = p.matcher(url);
        String m = mc.find() ? mc.group(1) : "";
        System.out.print(m.toUpperCase());

        // String v = "000001";
        // System.out.print(v.matches("^[0-9]{6}$"));

        // String temp = "\f234";
        // System.out.println(temp);
        // System.out.println(temp.replaceAll("\\f", "\\\\f"));
    }
}
