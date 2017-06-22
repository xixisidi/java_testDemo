package hadoop.dfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileIOTest {

    private String nameNode = "hdfs://192.168.60.185:9000/";
    
    /**
     * 上传文件
     */
    @SuppressWarnings("unused")
    private void upLoadFile() throws FileNotFoundException, IOException {
        //地址设置
        String srcPath = "F:\\cloudTolocal.txt";
        String destPath = nameNode + "user/zhangxz/in/FFFF.txt";
        
        //实例化一个文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(nameNode), conf);
        
        //输入输出流设置
        InputStream in = new BufferedInputStream(new FileInputStream(srcPath));
        OutputStream out = fs.create(new Path(destPath), new Progressable() {
            @Override
            public void progress() {
                System.out.println("上传完成一个文件到HDFS");
            }
        });
        
        // 上传文件
        IOUtils.copyBytes(in, out, 1024, true);
        
        System.out.println("上传成功");
    }

    /**
     * 下载文件
     */
    @SuppressWarnings("unused")
    private void downLoadFile() throws FileNotFoundException, IOException {
        //地址
        String srcPath =  nameNode + "user/zhangxz/in/text1.txt";
        String destPath = "F:\\cloudTolocal.txt";
        
        //实例化一个文件系统
        Configuration conf = new  Configuration();
        FileSystem fs = FileSystem.get(URI.create(nameNode), conf);
        
        //输入输出流设置
        FSDataInputStream in = fs.open(new Path(srcPath));
        OutputStream out = new FileOutputStream(destPath);
        
        //文件复制到本地
        IOUtils.copyBytes(in, out, 1024, true);
        
        System.out.println("下载成功");
    }
    
    /**
     * 下载文件
     */
    private void deleteFile() throws IOException{
        String destPath = nameNode + "user/zhangxz/in/FFFF.txt";
        
        //实例化一个文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(nameNode), conf);
        
        //删除文件
        boolean r = fs.delete(new Path(destPath), true);
        
        System.out.println(r);
        System.out.println("删除成功");
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileIOTest uploadAndDown = new FileIOTest();
        
        // 上传文件
        //uploadAndDown.upLoadFile();
        
        // 下载文件
        //uploadAndDown.downLoadFile();
        
        //删除文件
        uploadAndDown.deleteFile();

    }

}
