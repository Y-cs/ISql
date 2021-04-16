package self.sqlSession;

import org.dom4j.DocumentException;
import self.config.XmlConfigBuilder;
import self.pojo.Configuration;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author Y-cs
 * @date 2021/4/3 0:15
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        //解析配置
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        //定义sqlSession
        return new DefaultSqlSessionFactory(configuration);
    }


}
