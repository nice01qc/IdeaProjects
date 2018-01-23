package com.baobaotao.dao;

import com.baobaotao.domain.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoaderListener;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate  jdbcTemplate;

    public int getMatchCount(String  userName,String password){
        String sqlStr = "select count(*) from t_user"+"where user_name = ?and password=?";
        return (Integer)jdbcTemplate.queryForMap(sqlStr,new Object[]{userName,password}).get("count(*)");
    }

    public User findUserByUserName(final String userName){
        String sqlStr = "select user_id,user_name,credits"+
                "from t_user where user_name = ?";
        final User user = new User();
        jdbcTemplate.query(sqlStr, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(userName);
                user.setCredits(resultSet.getInt("credits"));
            }
        });
        return user;
    }
}
