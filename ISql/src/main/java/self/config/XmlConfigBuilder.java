package self.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import self.io.ResolverUtil;
import self.io.Resources;
import self.pojo.Configuration;
import self.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Y-cs
 * @date 2021/4/3 0:18
 */
public class XmlConfigBuilder {

    private final Configuration configuration;

    public XmlConfigBuilder() {
        configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException, IntrospectionException, ClassNotFoundException, NoSuchMethodException {

        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();

        readAndSetDataSource(rootElement.selectNodes("//property"));
        //mapper解析
        readAndSetMappers(rootElement.selectNodes("//mappers"));
        return configuration;
    }

    private void readAndSetDataSource(List<? extends Element> propertyElements) throws PropertyVetoException {
        Properties properties = new Properties();
        for (Element element : propertyElements) {
            String value = element.attributeValue("value");
            String name = element.attributeValue("name");
            properties.setProperty(name, value);
        }
        //处理连接信息
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driver"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("url"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);
    }

    private void readAndSetMappers(List<? extends Element> mapperElements) throws DocumentException, IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        for (Element mapperElement : mapperElements) {
            List<Element> packages = mapperElement.selectNodes("//package");
            for (Element pkg : packages) {
                String result = pkg.attributeValue("result");
                String xmlPath = result.replace(".", "/");
                List<InputStream> inputStreams = Resources.getSteamByFilePath(xmlPath);
                for (InputStream inputStream : inputStreams) {
                    XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
                    xmlMapperBuilder.parse(inputStream);
                }
            }

            List<Element> mappers = mapperElement.selectNodes("//mapper");
            for (Element mapper : mappers) {
                String result = mapper.attributeValue("result");
                InputStream resourceAsSteam = Resources.getResourceAsSteam(result);
                XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
                xmlMapperBuilder.parse(resourceAsSteam);
            }
        }


    }


}
