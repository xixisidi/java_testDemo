/* 
 * @(#)SizeOfObject.java    Created on 2014-3-18
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package calculate_memory_size;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-3-18 下午5:24:09 $
 */
public class SizeOfObject extends SizeOf {      
     
    @Override     
    protected Object newInstance() {      
        return new Object();
    }      
     
    public static void main(String[] args) throws Exception {      
        SizeOf sizeOf = new SizeOfObject();      
        System.out.println("所占内存：" + sizeOf.size() + "字节");      
    }      
}