package memcache;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alisoft.xplatform.asf.cache.memcached.client.MemCachedClient;
import com.alisoft.xplatform.asf.cache.memcached.client.SockIOPool;
import com.net.zxz.bean.User;
import com.net.zxz.dao.UserDao;

public class MemcachedTest {

    protected static MemcachedTest instance = new MemcachedTest();

    // 创建MemCachedClient全局对象
    private static MemCachedClient mcc = new MemCachedClient();
    static {
        // 创建服务器列表及其权重
        String[] servers = { "127.0.0.1:11211" };
        Integer[] weights = { 3 };

        // 创建Socket连接池对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setFailover(true);

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // 设置主线程睡眠时间
        pool.setMaintSleep(30);

        // 设置TCP参数、连接超时等
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);
        pool.setAliveCheck(true);

        // 初始化连接池
        pool.initialize();

        // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
        mcc.setCompressEnable(true);
        mcc.setCompressThreshold(64 * 1024);
    }

    /*
     * 获取缓存调用的实例
     */
    public static MemcachedTest getInstance() {
        return instance;
    }

    /*
     * 添加缓存
     */
    public boolean add(String key, Object value) {
        return mcc.add(key, value);
    }

    /*
     * 添加缓存
     */
    public boolean add(String key, Object value, Date expiry) {
        return mcc.add(key, value, expiry);
    }

    /*
     * 修改缓存
     */
    public boolean replace(String key, Object value) {
        return mcc.replace(key, value);
    }

    /*
     * 修改缓存
     */
    public boolean replace(String key, Object value, Date expiry) {
        return mcc.replace(key, value, expiry);
    }

    /*
     * 刷新缓存
     */
    public boolean flushCache(String[] keys) {
        return mcc.flushAll(keys);
    }

    /*
     * 根据指定的关键字获取对象
     */
    public Object get(String key) {
        return mcc.get(key);
    }

    /*
     * 测试
     */
    public static void main(String[] args) {
        // 得到MemcachedManager实例
        MemcachedTest cache = MemcachedTest.getInstance();

        // 创建UserDao对象
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext("E:\\MyWork\\sshDemo\\testDemo\\web\\WEB-INF\\applicationContext.xml");
        UserDao userDao = webApplicationContext.getBean(UserDao.class);

        // 得到集合对象
        List<User> userList = userDao.listUser();

        // 创建User对象
        User user = null;

        for (int i = 0; i < userList.size(); i++) {
            // 循环遍历集合对象
            user = userList.get(i);

            // 存入缓存中
            cache.add("userList" + i, user.getUserName());

            // 从缓存中取出
            String uname = (String) cache.get("userList" + i);
            System.out.println("从缓存中取得的集合为：" + uname);
        }

    }
}
