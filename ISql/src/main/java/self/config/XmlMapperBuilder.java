package self.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;
import self.pojo.Configuration;
import self.pojo.MappedStatement;
import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;
import sun.security.krb5.Config;

import java.io.InputStream;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 0:37
 */
public class XmlMapperBuilder {

    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException {

        Document mapperXml = new SAXReader().read(in);
        Element rootElement = mapperXml.getRootElement();
        List<Element> selectElement = rootElement.selectNodes("//select");
        String namespace = rootElement.attributeValue("namespace");

        for (Element element : selectElement) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            configuration.getMappersMap().put(getStatementKey(namespace, id), mappedStatement);
        }
    }

    private String getStatementKey(String namespace, String id) {
        return namespace + "." + id;
    }


}
