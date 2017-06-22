package hadoop.hbase.test2;

import java.io.IOException; 
//import java.util.Map; 
//  
//import org.apache.hadoop.conf.Configuration; 
//import org.apache.hadoop.hbase.HBaseConfiguration; 
//import org.apache.hadoop.hbase.HColumnDescriptor; 
//import org.apache.hadoop.hbase.HTableDescriptor; 
//import org.apache.hadoop.hbase.client.HBaseAdmin; 
//import org.apache.hadoop.hbase.client.HTable; 
//import org.apache.hadoop.hbase.client.Put; 
//import org.apache.hadoop.hbase.client.Result; 
  
public class Htable { 
  
    public static void main(String[] args) throws IOException { 
//        Configuration hbaseConf = HBaseConfiguration.create(); 
//          
//        Configuration HBASE_CONFIG = new Configuration();   
//        //与hbase/conf/hbase-site.xml中hbase.master配置的值相同    
//        HBASE_CONFIG.set("hbase.master", "9.186.89.27:60000");   
//        //与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置的值相同    
//        HBASE_CONFIG.set("hbase.zookeeper.quorum", "9.186.89.27,9.186.89.29,9.186.89.31,9.186.89.33,9.186.89.34");   
//        //与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同   
//        HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2181");   
//        Configuration hbaseConf = HBaseConfiguration.create(HBASE_CONFIG);   
//  
//        HBaseAdmin admin = new HBaseAdmin(hbaseConf); 
//        // set the name of table 
//        HTableDescriptor htableDescriptor = new HTableDescriptor("test11".getBytes()); 
//        // set the name of column clusters 
//        htableDescriptor.addFamily(new HColumnDescriptor("cf1")); 
//        if (admin.tableExists(htableDescriptor.getName())) { 
//            admin.disableTable(htableDescriptor.getName()); 
//            admin.deleteTable(htableDescriptor.getName()); 
//        } 
//        // create a table 
//        admin.createTable(htableDescriptor); 
//        // get instance of table. 
//        HTable table = new HTable(hbaseConf, "test11"); 
//        // for is number of rows 
//        for (int i = 0; i < 3; i++) { 
//            // the ith row 
//            Put putRow = new Put(("row" + i).getBytes()); 
//            // set the name of column and value. 
//            putRow.add("cf1".getBytes(), (i+"col1").getBytes(), (i+"vaule1").getBytes()); 
//            putRow.add("cf1".getBytes(), (i+"col2").getBytes(), (i+"vaule2").getBytes()); 
//            putRow.add("cf1".getBytes(), (i+"col3").getBytes(), (i+"vaule3").getBytes()); 
//            table.put(putRow); 
//        } 
//  
//        // get data of column clusters 
//        for (Result result : table.getScanner("cf1".getBytes())) { 
//            // get collection of result 
//            for (Map.Entry<byte[], byte[]> entry : result.getFamilyMap("cf1".getBytes()).entrySet()) { 
//                String column = new String(entry.getKey()); 
//                String value = new String(entry.getValue()); 
//                System.out.println(column + "," + value); 
//            } 
//        } 
    } 
}  


