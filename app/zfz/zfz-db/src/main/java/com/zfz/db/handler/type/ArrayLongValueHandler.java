package com.zfz.db.handler.type;

import com.zfz.common.util.NumUtil;
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
 * 存储格式为1,2,3,4,5以便于select in时直接使用
 */
@MappedTypes(Long[].class)
public class ArrayLongValueHandler implements TypeHandler<Long[]> {

    @Override
    public Long[] getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return toLong(str);
    }

    @Override
    public Long[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        String re = rs.getString(columnIndex);
        return toLong(re);
    }

    @Override
    public Long[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String re = cs.getString(columnIndex);
        return toLong(re);
    }


    /**
     * 定义当前数据如何保存到数据库中
     */
    @Override
    public void setParameter(PreparedStatement pstmt, int index, Long[] value, JdbcType jdbcType) throws SQLException {
        if (value == null) {//判断传入的参数值是否为null
            pstmt.setString(index, "");//设置当前参数的值为空字符串
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                if (i != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(value[i]);
            }
            pstmt.setString(index, stringBuffer.toString());
        }
    }

    private Long[] toLong(String str) {
        List<Long> list = new ArrayList<Long>();
        try {
            if (StringUtils.isNotBlank(str)) {
                if (str.startsWith(",")) {
                    str = str.substring(1);
                }
                String[] data = str.split(",");
                for (String v : data) {
                    list.add(NumUtil.toLong(v));
                }

            }
        } catch (Exception ex) {

        }
        return list.toArray(new Long[list.size()]);
    }

}