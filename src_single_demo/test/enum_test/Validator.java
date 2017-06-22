/* 
 * @(#)Validator.java    Created on 2014-10-7
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package test.enum_test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-10-7 上午9:21:33 $
 */
public enum Validator {
    NUll {
        @Override
        public String getNameValue() {
            return "null";
        }

        @Override
        public boolean test(Object obj, String v) {
            if ("false".equals(v) && obj == null) {
                return false;
            }
            return true;
        }
    },
    TYPE {
        @Override
        public String getNameValue() {
            return "type";
        }

        @Override
        public boolean test(Object obj, String v) {
            if (v.equals("string") && obj instanceof String) {
                return true;
            }
            if (v.equals("date") && obj instanceof Date) {
                return true;
            }
            if (v.equals("int") && obj instanceof Integer) {
                return true;
            }
            if (v.equals("long") && obj instanceof Long) {
                return true;
            }
            if (v.equals("float") && obj instanceof Float) {
                return true;
            }
            if (v.equals("double") && obj instanceof Double) {
                return true;
            }
            if (v.equals("boolean") && obj instanceof Boolean) {
                return true;
            }
            return false;
        }
    },
    RANGE {
        @Override
        public String getNameValue() {
            return "range";
        }

        @Override
        public boolean test(Object obj, String v) {
            if (Integer.parseInt(v) < obj.toString().length()) {
                return false;
            }
            return true;
        }
    };

    public abstract boolean test(Object obj, String v);

    public abstract String getNameValue();

    Map<String, Validator> map;

    public Validator getValidatorByName(String name) {
        if (map == null) {
            map = new HashMap<String, Validator>();
            for (Validator item : Validator.values()) {
                map.put(item.getNameValue(), item);
            }
            System.out.print(1);
        }
        return map.get(name);
    }
}
