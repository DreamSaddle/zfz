package com.zfz.db.handler.type;

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
 * 存储格式为abc,efg,hij
 * 若有特色符号的字符串，请使用list<String>
 * <p>
 * 建议优先使用list<String>
 */

@MappedTypes(String[].class)
public class ArrayStringValueHandler implements TypeHandler<String[]> {

    @Override
    public String[] getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return toString(str);
    }

    @Override
    public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        String re = rs.getString(columnIndex);
        return toString(re);
    }

    @Override
    public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String re = cs.getString(columnIndex);
        return toString(re);
    }


    /**
     * 定义当前数据如何保存到数据库中
     */
    @Override
    public void setParameter(PreparedStatement pstmt, int index, String[] value, JdbcType jdbcType) throws SQLException {
        if (value == null) {//判断传入的参数值是否为null
            pstmt.setString(index, "");//设置当前参数的值为空字符串
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                if (StringUtils.isNotBlank(value[i])){
                    if (i != 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(value[i]);
                }
            }
            String str = stringBuffer.toString();
            if (str.indexOf(",")==0){
                str = str.substring(1);
            }
            pstmt.setString(index,str);
        }
    }


    private String[] toString(String str) {
        List<String> list = new ArrayList<String>();
        try {
            if (StringUtils.isNotBlank(str)) {
                if (str.startsWith(",")) {
                    str = str.substring(1);
                }
                String[] data = str.split(",");
                for (String v : data) {
                    list.add(v);
                }

            }
        } catch (Exception ex) {

        }
        return list.toArray(new String[list.size()]);
    }

}