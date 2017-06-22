/* 
 * @(#)Template.java    Created on 2014-4-3
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package freemark_template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-4-3 下午4:55:39 $
 */
public class MyTemplate {
    public static void main(String args[]) throws IOException, TemplateException{
        new MyTemplate().getR();
    }
    
    public void getR() throws IOException, TemplateException{
        Configuration cfg = new Configuration();
        String templateStr="Hello ${user}";
        Template t = new Template("name", new StringReader(templateStr),cfg);
        
        StringWriter writer = new StringWriter();
        
        Map root = new HashMap(); 
        root.put("user", "司法所第三方说法");
        
        t.process(root, writer);
        System.out.println(writer.toString());
    }
}
