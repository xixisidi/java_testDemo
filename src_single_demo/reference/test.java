package reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import com.net.zxz.bean.User;

public class test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//软引用所指向的对象按照 JVM 的使用情况（Heap 内存是否临近阈值）来决定是否回收。
		SoftReference<User> user_soft = new SoftReference<User>(new User()); 
		user_soft.get().setMainPhone(12313l);
		
		//只要进行系统垃圾回收，不管内存使用情况如何，永远对其进行回收
		WeakReference<User> user_weak = new WeakReference<User>(new User()); 
		User user = user_weak.get();
		user.setMainPhone(12313l);
		
		//垃圾回收处置
		test t = new test();
		t = null;
		System.gc();
		System.out.println("执行垃圾回收");
		
		System.out.println("user_soft:" + user_soft.get());
		System.out.println("user_weak(被局部引用):" + user_weak.get());
		
		user = null;
		System.gc();
		System.out.println("user_weak(已释放局部引用)::" + user_weak.get());
	}
	
	protected void finalize() throws Throwable { 
		System.out.println("执行finalize");
	}
}
