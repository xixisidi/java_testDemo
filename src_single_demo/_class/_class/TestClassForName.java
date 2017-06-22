package _class._class;

import com.net.zxz.bean.User;

public class TestClassForName {
	public static void  main(String[] args) throws Exception{
		// 通过类名创建实例
		System.out.println("/************通过类名创建实例：S**************/");
		Class<?> cls = Class.forName("com.net.zxz.bean.User");
		User user = (User)cls.newInstance();
		user.setUserName("zhangxz");
		System.out.println(user.getUserName());
		System.out.println("/************通过类名创建实例：E**************/");
		
		System.out.println("");
		System.out.println("类名：" + cls.getName());
		System.out.println("父类：" + cls.getSuperclass().getName());
		System.out.println("是否是接口：" + cls.isInterface());
		System.out.println("ClassLoader：" + cls.getClassLoader().getClass().getName());
	}
}
