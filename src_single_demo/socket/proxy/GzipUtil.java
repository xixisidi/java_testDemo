package socket.proxy;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

public class GzipUtil {
    // http结束符
    private static final byte[] HTTP_BODY_END = new byte[] { 0x0d, 0x0a, 0x30, 0x0d, 0x0a, 0x0d, 0x0a };
    private final String path;
    private String gzipPath;
    private final int index = 0;
    private int headerEnd = 0;

    public GzipUtil(String path) {
        this.path = path;
    }

    /**
     * 创建产生gzip
     */
    public void createGzip() {
        File file = new File(path);
        FileInputStream is = null;
        RandomAccessFile randomFile = null;
        gzipPath = path.substring(0, path.indexOf(".data")) + ".zip";
        try {
            // 保存文件
            randomFile = new RandomAccessFile(gzipPath, "rw");

            // 跳到http开头
            is = new FileInputStream(file);
            is.skip(index);

            byte[] buffer = new byte[1024];
            int n = is.read(buffer);
            int chunkLengthStart = -1;
            int chunkLengthEnd = -1;
            long chunkStart = 0;
            long chunkEnd = 0;
            boolean httpStart = true;
            for (int i = 0; i < n; i++) {
                if (httpStart) {
                    if (buffer[i] != 0x0d) {
                        continue;
                    }
                    if (buffer[i + 1] != 0x0a) {
                        continue;
                    }
                    if (buffer[i + 2] != 0x0d) {
                        continue;
                    }
                    if (buffer[i + 3] != 0x0a) {
                        continue;
                    }

                    headerEnd = i - 1;
                    // chunk开始
                    chunkLengthStart = i + 4;
                    httpStart = false;
                    // System.out.println("--+++-" + chunkLengthStart);
                }
                else {
                    if (buffer[i] != 0x0d) {
                        continue;
                    }
                    if (buffer[i + 1] != 0x0a) {
                        continue;
                    }

                    chunkLengthStart = i + 2;
                    // System.out.println("----" + chunkLengthStart);
                }

                for (int j = 0; j < 10; j++) {
                    if (buffer[chunkLengthStart + j] != 0x0d) {
                        continue;
                    }
                    if (buffer[chunkLengthStart + j + 1] != 0x0a) {
                        continue;
                    }
                    // 找chunk结尾
                    chunkLengthEnd = chunkLengthStart + j - 1;
                    chunkStart += chunkLengthStart + j + 2;
                    break;
                }

                // 获取该chunk长度
                // System.out.println(chunkLengthEnd);
                int length = chunkLengthEnd - chunkLengthStart + 1;
                // System.out.println("______)))))))" + length);
                byte[] lengthStr = new byte[length];
                for (int j = 0; j < length; j++) {
                    lengthStr[j] = buffer[chunkLengthStart + j];
                }
                long chunkLength = Long.valueOf(new String(lengthStr), 16);
                chunkEnd = chunkStart + chunkLength - 1;

                // 复制文件
                is.close();
                is = new FileInputStream(file);
                is.skip(chunkStart);
                while (chunkLength > 0) {
                    n = is.read(buffer, 0, buffer.length);
                    // System.out.println(new String(buffer));
                    chunkLength -= n;
                    if (chunkLength <= -1) {
                        n += chunkLength;
                    }
                    randomFile.write(buffer, 0, n);
                }

                // 下一个chunk
                is.close();
                is = new FileInputStream(file);
                is.skip(chunkEnd + 1);
                n = is.read(buffer, 0, buffer.length);
                i = -1;
                chunkStart = chunkEnd + 1;
                // System.out.println("===========================" + chunkEnd);
                // System.out.println(new String(buffer));

                // 是否已结束
                for (int j = 0; j < HTTP_BODY_END.length; j++) {
                    // 不是http结束
                    if (buffer[j] != HTTP_BODY_END[j]) {
                        break;
                    }

                    if (j == HTTP_BODY_END.length - 1) {
                        return;
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception e2) {
                }
            }

            if (randomFile != null) {
                try {
                    randomFile.close();
                }
                catch (Exception e2) {
                }
            }
        }
    }

    /**
     * 打印头信息
     */
    public void printHeader() {
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[headerEnd + 1];
            int n = in.read(buffer);
            if (n > 0) {
                out.write(buffer, 0, n);
            }
            System.out.println("Header_S========================================================");
            System.out.println(out.toString());
            System.out.println("Header_E========================================================");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e2) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (Exception e2) {
                }
            }
        }
    }

    /**
     * 打印压缩内容
     */
    public static String parseAsString(String gzipPath) {
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        GZIPInputStream gunzip = null;
        try {
            in = new FileInputStream(gzipPath);
            out = new ByteArrayOutputStream();
            gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e2) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (Exception e2) {
                }
            }
            if (gunzip != null) {
                try {
                    gunzip.close();
                }
                catch (Exception e2) {
                }
            }
        }
        
        return null;
    }
    
    /**
     * 获取非压缩文件
     * @param filePath
     * @return
     */
    public static String getAsString(String filePath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            return "";
        }
        StringBuffer context = new StringBuffer();
        BufferedReader input = null;
        try {
            // 指定UTF-8编码读文件
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsoluteFile()),
                    Charset.forName("UTF-8")));
            String line = null;
            while ((line = input.readLine()) != null) {
                context.append(line + "\r\n");
            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        finally {
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
        return context.toString();
    }

    public static void main(String[] args) {
        GzipUtil gzipParser = new GzipUtil("F:\\temp\\1\\MVT0XQG2KY2P8EVGG6AKZ8EQY1KLAOL6.data");
        gzipParser.createGzip();
        //gzipParser.printGzip();
    }
}
