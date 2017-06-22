package spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-29 下午5:56:05 $
 */
public class test {
    public static void main(String[] aa) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src_single_demo/spring/aop/applicationContext.xml");
        IStudent person = (IStudent) ctx.getBean("student");
        person.addStudent("dragon");
        System.out.println("================================");
        person.addStudent("dragon111");
        
    }
}
