package remote_involve.taobao_DUBBO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午6:08:42 $
 */
public class test {
   public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("src_single_demo/taobao_DUBBO/applicationConsumer.xml");
        DemoService demoService = (DemoService) context.getBean("demoService");
        demoService.sayHello();
   }
}
