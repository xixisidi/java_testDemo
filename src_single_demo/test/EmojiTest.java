package test;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiTest {
    public static void main(String args[]) throws UnsupportedEncodingException {
        char emoji = '\udfff';
        // char emoji1 = (char) 0x4e01;
        // String emoji2 = new String("丁".getBytes("UTF-8"), "iso-8859-1");
        //
        // System.out.println(bytesToHexString("丁".getBytes("iso-8859-1")));
        // System.out.println(bytesToHexString((emoji + "").getBytes("utf-8")));
        // System.out.println(Integer.toHexString(emoji));
        System.out.println(emoji + "_" + Integer.toHexString(emoji));
        // System.out.println(emoji);
        //
        // System.out.println(URLEncoder.encode(emoji + "", "utf-8"));

        String content = "哈哈哈哈[nt_000]";
        System.out.println(getEmotionContent(content));
    }

    private static final String emojiImage = "<img src='http://wwww.baid.com/image_xx_${index}.png' />";

    public static String getEmotionContent(String content) {
        Pattern emoji = Pattern.compile("\\[nt_[0-9]{3}\\]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcherEmotion = emoji.matcher(content);
        Set<String> emojiSet = new HashSet<String>();
        String emojiImage = null;
        while (matcherEmotion.find()) {
            String key = matcherEmotion.group();
            String emotion = key.substring(4, 7);
            if (emojiSet.contains(emotion)) {
                continue;
            }
            emojiSet.add(emotion);
            emojiImage = emojiImage.replace("${index}", emotion);
            content = content.replaceAll("\\[nt_" + emotion + "\\]", emojiImage);
        }
        return content;
    }
}
