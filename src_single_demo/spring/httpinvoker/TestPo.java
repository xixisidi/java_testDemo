/* 
 * @(#)TestPo.java    Created on 2014-5-13
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.httpinvoker;

import java.io.Serializable;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午6:08:42 $
 */
public class TestPo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String v;
    
    public TestPo(String v){
        this.v = v;
    }
    
    public String getDesp(){
        return v;
    }
}
