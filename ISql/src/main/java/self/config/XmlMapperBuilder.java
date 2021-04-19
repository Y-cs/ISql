package self.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import self.pojo.*;
import self.utils.GlobalKeyUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Y-cs
 * @date 2021/4/3 0:37
 */
public class XmlMapperBuilder {

    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException, IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        Document mapperXml = new SAXReader().read(in);
        Element rootElement = mapperXml.getRootElement();
        final String namespace = rootElement.attributeValue("namespace");
        readAndAddInsertMapper(namespace, rootElement.selectNodes("//insert"));
        readAndAddDeleteMapper(namespace, rootElement.selectNodes("//delete"));
        readAndAddUpdateMapper(namespace, rootElement.selectNodes("//update"));
        readAndAddSelectMapper(namespace, rootElement.selectNodes("//select"));
    }

    private void readAndAddUpdateMapper(String namespace, List<? extends Element> updateElement) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(namespace, element);
            mappedStatement.setMapperType(MapperTypeEnum.UPDATE);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddDeleteMapper(String namespace, List<? extends Element> updateElement) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(namespace, element);
            mappedStatement.setMapperType(MapperTypeEnum.DELETE);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddInsertMapper(String namespace, List<? extends Element> updateElement) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        for (Element element : updateElement) {
            MappedStatement mappedStatement = buildMapper(namespace, element);
            mappedStatement.setMapperType(MapperTypeEnum.INSERT);
            configuration.getMappersMap().put(GlobalKeyUtil.getStatementKey(namespace, mappedStatement.getId()), mappedStatement);
        }
    }

    private void readAndAddSelectMapper(String namespace, List<? extends Element> selectElement) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        for (Element element : selectElement) {
            MappedStatement mappedStatement = buildMapper(namespace, element);
            mappedStatement.setMapperType(MapperTypeEnum.SELECT);
            configuration.getMappersMap().put(mappedStatement.getGlobalId(), mappedStatement);
        }
    }

    private MappedStatement buildMapper(String namespace, Element element) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException {
        MappedStatement mappedStatement = new MappedStatement();
        String id = element.attributeValue("id");
        mappedStatement.setId(id);
        String statementKey = GlobalKeyUtil.getStatementKey(namespace, id);
        mappedStatement.setGlobalId(statementKey);
        mappedStatement.setResultType(element.attributeValue("resultType"));
        mappedStatement.setParameterTypes(parseParameter(namespace, id, element));
        mappedStatement.setSql(element.getTextTrim());
        return mappedStatement;
    }

    private ParameterType[] parseParameter(String clsName, String method, Element element) throws ClassNotFoundException, NoSuchMethodException, IntrospectionException {
        List<ParameterType> parameterTypes = new ArrayList<>();
        String parameterType = element.attributeValue("parameterType");
        Class<?> cls = Class.forName(clsName);
        BeanInfo beanInfo = Introspector.getBeanInfo(cls);
//        beanInfo
        Method declaredMethod = cls.getDeclaredMethod(method);


//        parameterTypes.add();

        return parameterTypes.toArray(new ParameterType[parameterTypes.size()]);
    }

    private void parseNamespace(String namespace) {
        try {
            if (this.configuration.getClassMapperMap().get(namespace) == null) {
                Class<?> cls = Class.forName(namespace);
                Method[] declaredMethods = cls.getDeclaredMethods();
                Map<String, Method> methodMap = new HashMap<>();
                for (Method declaredMethod : declaredMethods) {
                    if (methodMap.put(declaredMethod.getName(), declaredMethod) == null) {
                        throw new RuntimeException("too many method name is " + declaredMethod.getName() + " class is " + cls.getName());
                    }
                }
                ClassMapper classMapper = new ClassMapper();
                classMapper.setCls(cls);
                classMapper.setMethodMap(methodMap);
                this.configuration.getClassMapperMap().put(namespace, classMapper);
            }
        } catch (ClassNotFoundException e) {


            System.out.println("namespace is not class path");
        }
    }

}
