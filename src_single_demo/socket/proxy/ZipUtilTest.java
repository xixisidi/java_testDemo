package socket.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

// 将一个字符串按照zip方式压缩和解压缩   
public class ZipUtilTest {
	// 测试方法
	public static void main(String[] args) throws IOException {
		File file = new File("F://2.data");
		FileInputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int)file.length()];
		is.read(bytes);
		
		try {
    		int offset = 0;
    		long l = bytes.length - 4;
    		for (int i = 0;i < l;i++) {
				if(bytes[i] != 0x0d){
					continue;
				}
				if(bytes[i + 1] != 0x0a){
					System.out.println(1);
					continue;
				}
				if(bytes[i + 2] != 0x1F){
					continue;
				}
				if(bytes[i + 3] != 0x8B){
					continue;
				}
				offset = i + 2;
				break;
			}
    		System.out.println(offset);
    		ByteArrayOutputStream out = new ByteArrayOutputStream();
    		ByteArrayInputStream in = new ByteArrayInputStream(bytes, offset, bytes.length - offset);
    		GZIPInputStream gunzip = new GZIPInputStream(in);
    		byte[] buffer = new byte[256];
    		int n;
    		while ((n = gunzip.read(buffer)) >= 0) {
    			out.write(buffer, 0, n);
    		}
    		System.out.println(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}