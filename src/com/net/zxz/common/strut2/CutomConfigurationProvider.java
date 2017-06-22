package com.net.zxz.common.strut2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.opensymphony.util.FileManager;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

/**
 * 解决struts2中自动加载模块的struts的配置文件
 * <p>
 * 注意：模块的配置文件必须为/config/action/*.xml
 * <p>
 * snle
 * 
 * @author
 */
public class CutomConfigurationProvider extends XmlConfigurationProvider {
    private static final String FILE_PATTERN = "classpath*:/conf/action/*.xml";

    private Set<String> loadedFileUrls = new HashSet<String>();

    public CutomConfigurationProvider() {
        Map<String, String> mappings = new HashMap<String, String>();
        mappings.put("-//OpenSymphony Group//XWork 2.1.3//EN", "xwork-2.1.3.dtd");
        mappings.put("-//OpenSymphony Group//XWork 2.1//EN", "xwork-2.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 2.0//EN", "xwork-2.0.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.1.1//EN", "xwork-1.1.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.1//EN", "xwork-1.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.0//EN", "xwork-1.0.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.0//EN", "struts-2.0.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1//EN", "struts-2.1.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN", "struts-2.1.7.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.3//EN", "struts-2.3.dtd");
        setDtdMappings(mappings);
    }

    @Override
    public boolean needsReload() {
        for (String url : loadedFileUrls) {
            if (FileManager.fileNeedsReloading(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Iterator<URL> getConfigurationUrls(String fileName) throws IOException {
        List<URL> urls = new ArrayList<URL>();
        Resource[] resources = getAllResourcesUrl();
        for (Resource resource : resources) {
            urls.add(resource.getURL());
            loadedFileUrls.add(resource.getURL().toString());
        }

        return urls.iterator();
    }

    /**
     * 获取系统中需要搜寻的struts的配置
     * 
     * @return
     * @throws IOException
     */
    private Resource[] getAllResourcesUrl() {
        ResourcePatternResolver resoler = new PathMatchingResourcePatternResolver();
        try {
            return resoler.getResources(FILE_PATTERN);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Resource[0];
    }
}
