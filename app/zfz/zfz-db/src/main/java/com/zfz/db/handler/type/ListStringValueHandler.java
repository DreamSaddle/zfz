package com.zfz.db.handler.type;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zfz.common.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储格式为标准的jsonArray,如：["","",]
 */

@MappedTypes(List.class)
public class ListStringValueHandler implements TypeHandler<List<String>> {

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return toList(str);
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String re = rs.getString(columnIndex);
        return toList(re);
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String re = cs.getString(columnIndex);
        return toList(re);
    }


    /**
     * 定义当前数据如何保存到数据库中
     */
    @Override
    public void setParameter(PreparedStatement pstmt, int index, List<String> value, JdbcType jdbcType) throws SQLException {
        if (value == null) {//判断传入的参数值是否为null
            pstmt.setString(index, "[]");//设置当前参数的值为空字符串
        } else {
            pstmt.setString(index, JacksonUtils.toJSONString(value));
        }
    }


    private List<String> toList(String str) {
        List<String> list = new ArrayList<String>();
        try {
            if (StringUtils.isNotBlank(str)) {
                JsonNode jsonNode = JacksonUtils.toJSONNode(str);
                for (int i = 0; i < jsonNode.size(); i++) {
                    list.add(jsonNode.get(i).textValue());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}