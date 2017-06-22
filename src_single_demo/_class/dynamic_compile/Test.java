/* 
 * @(#)Test.java    Created on 2014-12-18
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package _class.dynamic_compile;

import java.util.Scanner;

public abstract class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("输入类名(全路径):");
        // _class.dynamic_compile.MyObj
        String fullClassName = input.nextLine();
        do {
            /*
             * 重新实例化ClassLoader 一个ClassLoader实例只能装载同一个类一次
             */
            MyClassLoader classLoader = new MyClassLoader();

            System.out.println("输入源代码(单行)：");
            // 输入
            // package _class.dynamic_compile;import _class.dynamic_compile.MyInterface;public class MyObj implements
            // MyInterface{public void sayHello(){System.out.println("Hello World");}}
            String code = input.nextLine();
            System.out.print("是否重新编译(y/n):");
            String recompile = input.nextLine();
            boolean compileBool = true;
            if (recompile.equalsIgnoreCase("y")) {
                compileBool = new MyClassCompiler(fullClassName.substring(fullClassName.lastIndexOf(".") + 1), code)
                        .compile();
                System.out.println(compileBool ? "编译成功" : "编译失败");
            }
            if (compileBool) {
                System.out.println("----------------------------");
                try {
                    MyInterface myObj = (MyInterface) classLoader.loadClass(fullClassName).newInstance();
                    myObj.sayHello();
                }
                catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("----------------------------");
            }
            System.out.print("continue?(y/n):");
        }
        while (input.nextLine().equalsIgnoreCase("y"));
    }

}
