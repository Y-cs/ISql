package self.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;
import self.pojo.Configuration;
import self.pojo.MappedStatement;
import self.pojo.MapperTypeEnum;
import self.utils.GlobalKeyUtil;

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
        final String namespace = rootElement.attributeValue("namespace");
        readAndAddInsertMapper(namespace, rootElement.selectNodes("//insert"));
        readAndAddDeleteMapper(namespace, rootElement.selectNodes("//delete"));
        readAndAddUpdateMapper(namespace, rootElement.selectNodes("//update"));
        readAndAddSelectMapper(namespace, rootElement.selectNodes("//select"));
    }

    private void readAndAddUpdateMapper(String namespace, List<? extends Element> updateElement) {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(element);
            mappedStatement.setMapperType(MapperTypeEnum.UPDATE);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddDeleteMapper(String namespace, List<? extends Element> updateElement) {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(element);
            mappedStatement.setMapperType(MapperTypeEnum.DELETE);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddInsertMapper(String namespace, List<? extends Element> updateElement) {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(element);
            mappedStatement.setMapperType(MapperTypeEnum.INSERT);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddSelectMapper(String namespace, List<? extends Element> selectElement) {
        for (Element element : selectElement) {
            MappedStatement mappedStatement = buildMapper(element);
            mappedStatement.setMapperType(MapperTypeEnum.SELECT);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private MappedStatement buildMapper(Element element) {
        MappedStatement mappedStatement = new MappedStatement();
        String id = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String parameterType = element.attributeValue("parameterType");
        String sqlText = element.getTextTrim();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParameterType(parameterType);
        mappedStatement.setSql(sqlText);
        return mappedStatement;
    }



}
