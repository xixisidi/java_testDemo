package socket.proxy;

import java.io.File;
import java.io.RandomAccessFile;

public class CourseCenterTest {
    static int i = 0;

    public static void saveFile(byte[] bytes, int startIndex, int length) {
        i++;
        try {
            String path = "G:\\courseCenter\\" + "" + i + ".zip";

            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            RandomAccessFile randomFile = new RandomAccessFile(path, "rw");

            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(bytes, startIndex, length);
            randomFile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
