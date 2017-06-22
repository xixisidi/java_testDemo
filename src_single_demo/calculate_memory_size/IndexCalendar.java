/*
 * @(#)IndexCalendar.java    Created on 2013-11-5
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package calculate_memory_size;

import java.util.List;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-5 下午3:07:39 $
 */
// 首页日历类
public class IndexCalendar extends SizeOf { // 8 + 4字节
    // private long peoples; // 8字节
    // private long repeoples; // 8字节
    //
    // private int day; // 4字节
    // private IndexCalendarType type = IndexCalendarType.AFTERNOON; // 4字节
    private String agencyName; // 4字节
    // private IndexCalendar indexCalendar; // 4字节
    //
    // private byte a; // 1字节
    // private byte a1;// 1字节
    // private byte a2;// 1字节
    // private byte a3;// 1字节

    private byte a4;// 1字节
    private byte a5;// 1字节
    private byte a6;// 1字节
    private byte a7;// 1字节

    private final Thread thread;

    private List<String> indexCalendarChild;// 4字节
    // private List<IndexCalendar> indexCalendarChild1;// 4字节
    // private List<IndexCalendar> indexCalendarChild2;// 4字节
    // private List<IndexCalendar> indexCalendarChild3;// 4字节

    public IndexCalendar() {
        // indexCalendarChild = new ArrayList<String>(); //默认10个地址 80字节 ?为什么不是40字节
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500000);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected Object newInstance() {
        return new IndexCalendar();
    }

    public IndexCalendar get1ad() {
        return new IndexCalendar();
    };

    public static void main(String[] args) throws Exception {
        IndexCalendar sizeOf = new IndexCalendar();
        int size = sizeOf.size();
        System.out.println("所占内存：" + size + "字节");
    }
}
