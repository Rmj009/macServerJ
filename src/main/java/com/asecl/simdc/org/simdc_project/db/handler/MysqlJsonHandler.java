package com.asecl.simdc.org.simdc_project.db.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//@MappedTypes(JSONObject.class)
//@MappedJdbcTypes(JdbcType.VARCHAR)
public class MysqlJsonHandler extends BaseTypeHandler<JSONObject> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject jsonObject, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, jsonObject.toJSONString());
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
       String sqlJson = resultSet.getString(s);
       if(null != sqlJson){
           return JSONObject.parseObject(sqlJson);
       }
       return null;
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if(null != sqlJson){
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if(null != sqlJson){
            return JSONObject.parseObject(sqlJson);
        }
        return null;
    }
}
