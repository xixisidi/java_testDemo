package com.net.zxz.dwr;

import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * dwr接口文件的生成器
 * 
 * <p>dwr接口文件的生成器，能将配置在dwr.xml中的所有js接口生成在一个js文件中。
 * 
 * <p>启动该生成器需要指定两个参数：
 * 
 * 	<li>--dwr.conf=dwr配置文件的绝对路径
 * 	<li>--out.js=生成的js文件的绝对路径
 * 
 * <p>需要注意的是，dwr.engine._execute的第一个path参数由一个全局变量dwr.engine._defaultPath给定
 * @author fangb
 * @date 2010-2-20 上午06:53:28
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class InterfaceJSGenerator {

	private static final Log log = LogFactory.getLog(InterfaceJSGenerator.class);
	
	/**
	 * dwr配置文件参数key
	 */
	private static final String DWR_CONF = "--dwr.conf";
	
	/**
	 * dwr的service定义配置文件参数key
	 */
	private static final String DWR_SERVICE_CONF = "--dwr.service.conf";
	
	/**
	 * 输出js文件路径参数key
	 */
	private static final String OUT_JS = "--out.js";
	
	private static final String SEPERATOR = "=";
	
	public static void main(String[] args){
		Map<String,String> argMap = new HashMap<String,String>();
		
		for(String arg:args){
			String[] argValues = StringUtils.split(arg, SEPERATOR);
			argMap.put(argValues[0], argValues[1]);
		}
		
		(new JSGenerator(argMap.get(DWR_CONF),argMap.get(DWR_SERVICE_CONF))).generateJS(argMap.get(OUT_JS));
	}
	
	/**
	 * 执行JS生成的实现类
	 * @author fangb
	 *
	 */
	static class JSGenerator{
		
		private String dwrConfig;
		private String dwrServiceConfig;
		
		private Map<String,Class> serviceInstances;
		
		/**
		 * @param dwrConfig dwr的配置文件路径 
		 */
		public JSGenerator(String dwrConfig,String dwrServiceConfig){
			this.dwrConfig = dwrConfig;
			this.dwrServiceConfig = dwrServiceConfig;
			
			this.serviceInstances = buildServiceInstances();
			
		}
		
		private Map<String,Class> buildServiceInstances(){
			log.debug("开始读取dwr的service配置文件，获取dwr的class定义...");
			
			Map<String,Class> beans = new HashMap<String,Class>();
			
			try {
				Document document = readDocument(this.dwrServiceConfig);

				List<Element> beanElements = getElementsByTagName(document, "bean");
				
				log.debug(beanElements.size());
				
				for(Element beanElement:beanElements){
					String bean = beanElement.attributeValue("id");
					String clazz = beanElement.attributeValue("class");

					beans.put(bean, Class.forName(clazz));
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			
			log.debug("dwr的service定义生成完成。");
			
			return beans;
		}
		
		/**
		 * 生成js，输出到jsFile指定的路径文件中，使用utf-8编码写入数据
		 * @param jsFile
		 */
		public void generateJS(String jsFile){
			File outFile = new File(jsFile);
			
			try{
				if(!outFile.exists()) outFile.createNewFile();
				FileUtils.writeStringToFile(outFile, generateJSData(),"UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		private Document readDocument(String file) throws DocumentException{
			SAXReader reader = new SAXReader();
			reader.setEntityResolver(new EntityResolver(){

				@Override
                public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					return new InputSource(new StringBufferInputStream(""));
				}
				
			});
			Document document = reader.read(new File(file));
			
			return document;
		}
		
		/**
		 * 生成js数据
		 * @return 返回生成的js数据字符串
		 * @throws DocumentException
		 */
		private String generateJSData() throws DocumentException{
			log.debug("开始读取dwr配置...");
			
			List<Element> elements = getElementsByTagName(readDocument(this.dwrConfig), "create");
			
			log.debug("读取dwr配置完成，开始生成每个接口的文件");
			log.debug("interface的数量："+elements.size());
			
			StringBuilder content = new StringBuilder();
			
			for(Element element:elements){
				if("spring".equals(element.attributeValue("creator")))
					content.append(generateOneInterface(element));
			}
			
			return content.toString();
		}
		
		/**
		 * 生成某一个create的接口js内容
		 * @param element
		 * @return
		 */
		private String generateOneInterface(Element element){
			String javascript = element.attributeValue("javascript");
			
			String beanName = "";
			List<Element> paramElements = getChildElementsByTagName(element, "param");
			for(Element paramElement:paramElements)
				if("beanName".equals(paramElement.attributeValue("name")))
					beanName = paramElement.attributeValue("value");
			
			List<String> methodNames = new ArrayList<String>();
			List<Element> methodElements = getChildElementsByTagName(element, "include");
			for(Element methodElement:methodElements)
				methodNames.add(methodElement.attributeValue("method"));
			
			InterfaceInfo info = new InterfaceInfo(javascript,beanName,methodNames);
			log.debug("开始输出接口："+javascript);
			String content = info.toJavascript();
			log.debug("接口内容为：\n"+content);
			
			return content;
		}
		
		/**
		 * 得到文档的某个标签的所有元素
		 * @param document
		 * @param tagName
		 * @return
		 */
		private List<Element> getElementsByTagName(Document document,String tagName){
			List<Element> elements = new ArrayList<Element>();
			
			elements.addAll(getChildElementsByTagName(document.getRootElement(), tagName));
			
			return elements;
		}
		
		/**
		 * 得到一个元素的某个标签的所有子元素
		 * @param element
		 * @param tagName
		 * @return
		 */
		private List<Element> getChildElementsByTagName(Element element,String tagName){
			List<Element> elements = new ArrayList<Element>();
			
			for(Object child:element.elements()){
				Element childElement = (Element)child;
				if(childElement.getName().equals(tagName)){
					elements.add(childElement);
				}else{
					elements.addAll(getChildElementsByTagName(childElement, tagName));
				}
			}
			
			return elements;
		}
		
		/**
		 * 某个create接口的信息类
		 * @author fangb
		 *
		 */
		class InterfaceInfo{
			private String javascript;
			private List<String> methodNames;

			Map<String,Method> methodMap = new HashMap<String,Method>();
			
			/**
			 * 
			 * @param javascript 该接口在js端的名称
			 * @param beanName 该接口的实现类标识
			 * @param methodNames 该接口拥有的方法
			 */
			public InterfaceInfo(String javascript,String beanName,List<String> methodNames){
				this.javascript = javascript;
				this.methodNames = methodNames;
				
				Class clazz = serviceInstances.get(beanName);
				if(clazz!=null){
					for(Method method:clazz.getMethods())
						methodMap.put(method.getName(), method);
				}
			}
			
			/**
			 * 将该接口转化为js代码
			 * @return
			 */
			public String toJavascript(){
				StringBuilder content = new StringBuilder();
				content.append(String.format("if (%s == null) var %s = {};", this.javascript,this.javascript));
				
				for(String methodName:this.methodNames)
					content.append(toMethodJavascript(methodName));
				
				return content.toString();
			}
			
			/**
			 * 转化某一个方法的js代码
			 * @param methodName
			 * @return
			 */
			public String toMethodJavascript(String methodName){
				Method method = methodMap.get(methodName);
				if(method==null)	return "";
				int paramSize = method.getParameterTypes().length;
				
				List<String> params = new ArrayList<String>();
				for(int i=0;i<paramSize;++i)
					params.add("p"+i);
				
				String paramValue = "";
				if(!params.isEmpty())
					paramValue = StringUtils.join(params, ",")+",";
				
				StringBuilder content = new StringBuilder();
				content.append(this.javascript);
				content.append(".");
				content.append(methodName);
				content.append("= function(");
				content.append(paramValue);
				content.append("callback){");
				content.append("dwr.engine._execute(");
				content.append("dwr.engine._defaultPath,");
				content.append("'"+this.javascript+"',");
				content.append("'"+methodName+"',");
				content.append(paramValue);
				content.append("callback);}\n");
				
				return content.toString();
			}
		}
	}
	
	
}
