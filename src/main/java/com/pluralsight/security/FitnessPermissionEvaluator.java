package com.pluralsight.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;
import java.io.Serializable;

public class FitnessPermissionEvaluator implements PermissionEvaluator{

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetFomainObject, Object permission) {

        JdbcTemplate template = new JdbcTemplate(dataSource);

        Object[] arrayArgs = {((User)authentication.getPrincipal()).getUsername(),
                targetFomainObject.getClass().getName(),
                permission.toString()};

        int count = template.queryForObject("select count(*) from permissions p " +
                "where p.username = ? nad p.target=? and p.permission=?", arrayArgs, Integer.class);

        return count == 1;

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable id, String type, Object permission) {
        return false;
    }
}
