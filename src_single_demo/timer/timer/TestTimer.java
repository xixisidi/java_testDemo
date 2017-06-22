/* 
 * @(#)MyTask.java    Created on 2014-10-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package timer.timer;

import java.util.Timer;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-10-20 下午7:53:51 $
 */
public class TestTimer {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Timer timer = new Timer();
        // timer.schedule(myTask, 1000, 1000);
        timer.schedule(myTask, 1000);
        // timer.
        System.out.println("main end");
    }
}
