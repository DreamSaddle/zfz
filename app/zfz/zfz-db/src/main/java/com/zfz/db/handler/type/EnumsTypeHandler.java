package com.zfz.db.handler.type;/*
package com.pm.framework.common.dao.handler.type;

import com.baomidou.mybatisplus.extension.handlers.EnumTypeHandler;
import com.pm.framework.common.bean.IEnums;
import com.pm.framework.common.dao.handler.EnumUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumsTypeHandler<E extends Enum<?> & IEnums> extends BaseTypeHandler<IEnums> {

    private Class<E> type;

    public EnumsTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        EnumTypeHandler enumTypeHandler;
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnums parameter, JdbcType jdbcType)
            throws SQLException {
        if (jdbcType == null) {
            ps.setObject(i, parameter.getValue());
        } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (null == rs.getString(columnName) && rs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(type, rs.getObject(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (null == rs.getString(columnIndex) && rs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(type, rs.getObject(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (null == cs.getString(columnIndex) && cs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(type, cs.getObject(columnIndex));
    }
}*/
