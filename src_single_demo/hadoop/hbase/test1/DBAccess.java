/* 
 * @(#)DBAccess.java    Created on 2013-12-27
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package hadoop.hbase.test1;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-27 下午5:46:31 $
 */
public class DBAccess {
    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(DBAccess.class);
        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(Text.class);
        conf.setInputFormat(DBInputFormat.class);
        FileOutputFormat.setOutputPath(conf,new Path("dboutput"));
        DBConfiguration.configureDB(conf,"com.mysql.jdbc.Driver","jdbc:mysql://localhost/school","root","123456");
        String [] fields = {"id","name", "age", "departmentID"};
        DBInputFormat.setInput(conf,TeacherRecord.class, "teacher",null, "id", fields);
        conf.setMapperClass(DBAccessMapper.class);
        conf.setReducerClass(IdentityReducer.class);
        JobClient.runJob(conf);

    }
}
