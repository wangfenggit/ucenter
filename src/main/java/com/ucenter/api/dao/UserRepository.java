package com.ucenter.api.dao;

import com.ucenter.api.entity.User;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangfeng on 2018/7/18.
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取用户列表
     *
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public List<User> getUserList() {
        List<User> userList = jdbcTemplate.query("select id,name,email from users", new UserRowMapper());
        return userList;
    }

    /**
     * 根据用户id获取用户
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public User getUserById(Integer id){
        //queryForObject:找不到会报异常  query:找不到则Null
        //User user=jdbcTemplate.queryForObject("select id,name,email from users where id=?",new Object[]{id},new UserRowMapper());
        List<User> userList = jdbcTemplate.query("select id,name,email from users where id=?", new Object[]{id}, new UserRowMapper());
        User user = null;
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return user;
    }

    /**
     * 插入用户数据
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int saveUser(final User user) throws Exception {
        int resRow = jdbcTemplate.update("INSERT INTO users(id,name,email) VALUES(NULL,?,?)", new Object[]{
                user.getName(), user.getEmail()
        });
        return resRow;
    }

    /**
     * 插入用户数据-防止sql注入
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int saveUserWithSafe(final User user) throws Exception {
        int resRow = jdbcTemplate.update("INSERT INTO users(id,name,email) VALUES(NULL,?,?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
            }
        });
        return resRow;
    }

    /**
     * 插入用户数据-防止sql注入-可以返回该条记录的主键（注意需要指定主键）
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = UserException.class)
    public int saveUserWithKey(final User user) throws Exception {
        String sql = "INSERT INTO users(id,name,email) VALUES(NULL,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resRow = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"}); //指定 id 为主键
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                return ps;
            }
        }, keyHolder);
        return Integer.parseInt(keyHolder.getKey().toString());
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public int updateUser(final User user) throws Exception {
        String sql = "update users set name=?,email=? where id=?";
        int resRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setInt(3, user.getId());
            }
        });
        return resRow;
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int deleteUser(final User user) throws Exception {
        int resRow = jdbcTemplate.update("DELETE FROM users WHERE id=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, user.getId());
            }
        });
        return resRow;
    }

    /**
     * 根据用户名查找用户-用于判断用户是否存在
     *
     * @param user
     * @return
     * @throws Exception
     */
    public User getUserByUserName(final User user) throws Exception {
        String sql = "select id,name,email from users where name=?";
        List<User> queryList = jdbcTemplate.query(sql, new UserRowMapper(), new Object[]{user.getName()});
        if (queryList != null && queryList.size() > 0) {
            return queryList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取记录数
     *
     * @return
     * @throws Exception
     */
    public Integer getCount() throws Exception {
        String sql = "select count(id) from users";
        //jdbcTemplate.getMaxRows();
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        return total;
    }


}
