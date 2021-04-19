package self.sqlSession;

import self.config.BoundSql;
import self.pojo.Configuration;
import self.pojo.MappedStatement;
import self.sqlSession.handler.ParameterHandler;
import self.sqlSession.handler.ResultHandler;
import self.sqlSession.hook.SqlSessionHook;
import self.utils.ReflexUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Y-cs
 * @date 2021/4/3 23:06
 */
public class SimpleExecutor implements Executor, SqlSessionHook {

    private ParameterHandler parameterHandler;

    private ResultHandler resultHandler;

    public SimpleExecutor() {
        parameterHandler = new ParameterHandler();
        resultHandler = new ResultHandler();
    }

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        Connection connection = null;
        try {
            //获取链接
            connection = configuration.getDataSource().getConnection();
            beginExecuteHook(connection, mappedStatement, params);
            //获取sql
            String sql = mappedStatement.getSql();
            //预处理
            BoundSql boundSql = BoundSql.getBoundSql(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            parameterHandler.handler(preparedStatement, boundSql, mappedStatement, params);
            //执行
            ResultSet resultSet = preparedStatement.executeQuery();

            return this.resultHandler.handlerResultSet(resultSet, mappedStatement);
        } finally {
            afterExecuteHook(connection, mappedStatement, params);
        }
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Connection connection = null;
        try {
            //获取链接
            connection = configuration.getDataSource().getConnection();
            beginExecuteHook(connection, mappedStatement, params);
            //获取sql
            String sql = mappedStatement.getSql();
            //预处理
            BoundSql boundSql = BoundSql.getBoundSql(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            this.parameterHandler.handler(preparedStatement, boundSql, mappedStatement, params);
            //执行
            //返回影响行数
            return preparedStatement.executeUpdate();
        } finally {
            afterExecuteHook(connection, mappedStatement, params);
        }
    }

    @Override
    public int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return this.update(configuration, mappedStatement, params);
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return this.update(configuration, mappedStatement, params);
    }


}
