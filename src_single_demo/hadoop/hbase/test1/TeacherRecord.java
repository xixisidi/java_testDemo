/* 
 * @(#)TeacherRecord.java    Created on 2013-12-27
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package hadoop.hbase.test1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-27 下午5:45:10 $
 * 来源：http://www.itpub.net/thread-1496569-1-1.html
 */
public class TeacherRecord implements Writable, DBWritable {
    int id;
    String name;
    int age;
    int departmentID;
    
    @Override
    public void readFields(DataInput in) throws IOException{
           this.id = in.readInt();
           this.name = Text.readString(in);
           this.age = in.readInt();
           this.departmentID =in.readInt();
    }


    @Override
    public void write(DataOutput out) throws IOException {
           out.writeInt(this.id);
           Text.writeString(out, this.name);
           out.writeInt(this.age);
           out.writeInt(this.departmentID);

    }


    @Override
    public void readFields(ResultSet result) throws SQLException {
           this.id = result.getInt(1);
           this.name =result.getString(2);
           this.age = result.getInt(3);
           this.departmentID =result.getInt(4);
    }


    @Override
    public void write(PreparedStatement stmt) throws SQLException {
           stmt.setInt(1, this.id);
           stmt.setString(2, this.name);
           stmt.setInt(3, this.age);
           stmt.setInt(4, this.departmentID);
    }


    @Override

    public String toString() {
           return new String(this.name+ " " + this.age + " " + this.departmentID);
    }
}
