/*
 * @(#)SerializableInnerClassTest.java    Created on 2016年3月7日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2016年3月7日 上午10:28:06 $
 */
public class SerializableInnerClassTest implements Serializable {
    public interface PersonInterface extends Serializable {
        public void p();
    }

    public static void main(String[] args) {
        new SerializableInnerClassTest().testSerializable();
    }

    public void testSerializable() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(out);
            oos.writeObject(new PersonInterface() {

                @Override
                public void p() {
                    System.out.println("123");
                }

            });
            oos.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
