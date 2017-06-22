/* 
 * @(#)MyTask.java    Created on 2014-10-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package timer.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-10-20 下午7:53:51 $
 */
public class MyTask extends TimerTask {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        System.out.println(sdf.format(new Date()));
    }
}
