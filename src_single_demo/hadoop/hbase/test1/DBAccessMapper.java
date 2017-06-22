/* 
 * @(#)DBAccessMapper.java    Created on 2013-12-27
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package hadoop.hbase.test1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-27 下午6:01:24 $
 */
public class DBAccessMapper extends MapReduceBase implements Mapper<LongWritable,TeacherRecord, LongWritable, Text> {
    @Override
    public void map(LongWritable key, TeacherRecord value, OutputCollector<LongWritable,Text> collector, Reporter reporter)throws IOException {
        collector.collect(new LongWritable(value.id),new Text(value.toString()));
    }
}
