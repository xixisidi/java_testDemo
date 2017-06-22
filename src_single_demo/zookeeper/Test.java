/* 
 * @(#)SimpleTest.java    Created on 2015-8-2
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author zhangxz
 */
public class Test {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
    	//创建一个Zookeeper实例，第一个参数为目标服务器地址和端口，第二个参数为Session超时时间，第三个为节点变化时的回调方法
    	ZooKeeper zk =  new ZooKeeper("127.0.0.1:2181", 500000, new Watcher(){
			@Override
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				
			}
    	});
    	
    	//创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
    	zk.create("/root", "mydata".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    	//在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
    	zk.create("/root/childone","zhangxz".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

    	//取得/root节点下的子节点名称,返回List<String>
    	List<String> r =  zk.getChildren("/root",true);
    	System.out.println("//取得/root节点下的子节点名称:" + StringUtils.join( r, ","));
    	
    	//取得/root/childone节点下的数据,返回byte[]
    	 byte[] b = zk.getData("/root/childone", true, null);
    	 System.out.println("//取得/root/childone节点下的数据:" + new String(b));

    	//修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
    	zk.setData("/root/childone","childonemodify".getBytes(), -1);

    	//删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
    	zk.delete("/root/childone", -1);

    	//关闭session
    	zk.close();
    }
}
