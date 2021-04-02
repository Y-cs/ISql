package self.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import self.io.Resources;
import self.pojo.Configuration;
import self.pojo.MappedStatement;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @author Y-cs
 * @date 2021/4/3 0:18
 */
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        List<Element> propertyElements = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : propertyElements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driver"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("url"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);
        configuration.setMappersMap(new HashMap<String, MappedStatement>());

        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperElements) {
            String result = mapperElement.attributeValue("result");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(result);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsSteam);


        }


        return configuration;
    }


}