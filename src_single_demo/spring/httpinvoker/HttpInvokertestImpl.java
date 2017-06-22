/* 
 * @(#)HttpInvokertestImpl.java    Created on 2014-5-13
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.httpinvoker;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午6:05:40 $
 */
public class HttpInvokertestImpl implements HttpInvokerTestI{
    @Override
    public TestPo getTestPo(String desp) {
        return new TestPo(desp);
    }
}
