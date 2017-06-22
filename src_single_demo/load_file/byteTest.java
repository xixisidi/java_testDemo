package load_file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;

public class byteTest {
    private static final String record = "f://record.txt";
    private static final String readPath = "G://record.txt";
    private static final String writePath = "f://write.avi";

    public static void main(String[] args) throws IOException {
        File file = new File(readPath);
        int position = 0;
        int index = 0;
        while ((position = readRecord()) < file.length()) {
            if (file.length() > position) {
                byte[] bytes = readbyte(file, position);
                File file2 = new File("G://ff/" + index + ".part");
                // writebyte(bytes);
                position += bytes.length;
                index++;
                FileUtils.writeByteArrayToFile(file2, bytes);
                writeRecord(position);
                System.out.println(new String(bytes));
            }
            else {
                System.out.println("文件读取完毕");
            }
        }
    }

    /**
     * 记录上一次文件读取位置
     * 
     * @param position
     * @throws IOException
     */
    public static void writeRecord(int position) throws IOException {
        RandomAccessFile randomFile = new RandomAccessFile(record, "rw");
        randomFile.write(new String().valueOf(position).getBytes());
        randomFile.close();
    }

    public static int readRecord() throws IOException {
        String position = new String(readbyte(record));
        if (position.equals("") || position == null) {
            return 0;
        }
        return Integer.parseInt(position);
    }

    public static byte[] readbyte(File file, int position) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        byte[] temp = new byte[1024];
        if (file.length() <= position) {
            return null;
        }
        in.skip(position);
        in.read(temp);
        in.close();
        return temp;
    }

    public static void writebyte(byte[] bytes) throws IOException {
        RandomAccessFile randomFile = new RandomAccessFile(writePath, "rw");
        // 文件长度，字节数
        long fileLength = randomFile.length();
        // 将写文件指针移到文件尾。
        randomFile.seek(fileLength);
        randomFile.write(bytes);
        randomFile.close();
    }

    public static byte[] readbyte(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        byte[] bytes = new byte[(int) file.length()];
        byte[] temp = new byte[10240];
        for (int i = 0; i < bytes.length; i = i + temp.length) {
            try {
                in.read(temp);
                for (int j = 0; j < temp.length && j + i < bytes.length; j++) {
                    bytes[j + i] = temp[j];
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        in.close();
        return bytes;
    }
}
