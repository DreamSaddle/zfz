package com.zfz.db.handler.type;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(String.class)
public class NullValueHandler implements TypeHandler<String> {

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        String re = rs.getString(columnName);
        re = StringUtils.trimToEmpty(re);
        return re;
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String re = rs.getString(columnIndex);
        re = StringUtils.trimToEmpty(re);
        return re;
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String re = cs.getString(columnIndex);
        re = StringUtils.trimToEmpty(re);
        return re;
    }

    /**
     * 定义当前数据如何保存到数据库中
     */
    @Override
    public void setParameter(PreparedStatement pstmt, int index, String value, JdbcType jdbcType) throws SQLException {
        //  if (value == null && jdbcType == JdbcType.VARCHAR) {//
        if (value == null) {//判断传入的参数值是否为null
            pstmt.setString(index, "");//设置当前参数的值为空字符串
        } else {
            pstmt.setString(index, value);//如果不为null，则直接设置参数的值为value
        }
    }

}