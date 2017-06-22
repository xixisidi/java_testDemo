package remote_involve.webservice;

import remote_involve.webservice.client.HelloService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午6:08:42 $
 */
public class test {
   public static void main(String[] args) {
         HelloService service = new HelloService();   
         remote_involve.webservice.client.Hello helloProxy = service.getHelloPort();   
         String hello = helloProxy.hello("你好");          
         System.out.println(hello);   
   }
}
