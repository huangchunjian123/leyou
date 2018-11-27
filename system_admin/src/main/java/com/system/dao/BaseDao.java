package com.system.dao;

import com.system.common.anotation.Column;
import com.system.common.anotation.Table;
import com.system.common.util.Page;
import com.system.common.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zzy
 */
@Slf4j
public class BaseDao<T> {
    /** 设置一些操作的常量 */
    private static final String SQL_INSERT = "insert";
    private static final String SQL_UPDATE = "update";
    private static final String SQL_DELETE = "delete";
    private static final String SQL_WHERE = "where";
    private static final String SQL_ORDER = "order";
    private static Pattern ORDER_BY_PATTERN = Pattern.compile(
            "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private String tableName;
    private String pkName;
    public RowMapper<T> rowMapper;



    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        log.info("Dao实现类是：" + entityClass.getName());

        //表名
        this.tableName = entityClass.getAnnotation(Table.class).name();
        log.info("表名是：" + this.tableName);

        this.getTablePkColumn();
        log.info("主键字段是：" + this.pkName);
    }

    public boolean insertEntity(T entity) {
        String sql = this.makeSql(SQL_INSERT,entity);
        Object[] args = this.setArgs(entity, SQL_INSERT);
        return jdbcTemplate.update(sql.toString(), args) > 0?true:false;
    }

    public boolean updateEntity(T entity) {
        String sql = this.makeSql(SQL_UPDATE,entity);
        Object[] args = this.setArgs(entity, SQL_UPDATE);
        return jdbcTemplate.update(sql.toString(), args) > 0?true:false;
    }

    public boolean deleteEntity(T entity) {
        String sql = this.makeSql(SQL_DELETE,entity);
        Object[] args = this.setArgs(entity, SQL_DELETE);
        return jdbcTemplate.update(sql.toString(), args) > 0?true:false;
    }

    /*public boolean deleteById(Serializable id) {
        String sql = " DELETE FROM " + this.tableName + " WHERE "+this.pkName+"=?";
        return jdbcTemplate.update(sql, id) > 0?true:false;
    }*/

    public boolean deleteById(Serializable ... ids){
        String args = "";

        for(Serializable id:ids){
            args += "?,";
        }

        String sql = " DELETE FROM " + this.tableName + " WHERE "+this.pkName+" in("+args.substring(0,args.length()-1)+")";


        return jdbcTemplate.update(sql, ids) > 0?true:false;
    }

    public boolean deleteTable() {
        String sql = " TRUNCATE TABLE " + this.tableName;
        return jdbcTemplate.update(sql) > 0?true:false;
    }

    public T findEntityById(Serializable id) {
        String sql = "SELECT * FROM " + this.tableName + " WHERE "+this.pkName+"=?";
        List<T> list =  jdbcTemplate.query(sql, this.rowMapper, id);
        return list.size() > 0?list.get(0):null;
    }

    public T findFirstEntity(String sql,Object ... args){

        List<T> list = jdbcTemplate.query(sql, args, this.rowMapper);

        return list.size() > 0?list.get(0):null;
    }

    public List<T> findAllEntity() {
        String sql = "SELECT * FROM " + this.tableName;
        return jdbcTemplate.query(sql, this.rowMapper);
    }

    public List<T> findEntity(String sql,Object ... args){
        return jdbcTemplate.query(sql, args, this.rowMapper);
    }
    public List<Map<String,Object>> select(String sql,Object ... args){
        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 获取实体分页 sql为where
     * @param whichPage
     * @param pageSize
     * @param whereSql
     * @param args
     * @return
     */
    public Page<T> paginate(int whichPage,int pageSize,String whereSql,Object ... args){

        if(!StrUtil.isBlank(whereSql) && whereSql.toLowerCase().indexOf(SQL_WHERE) < 0 && whereSql.toLowerCase().trim().indexOf(SQL_ORDER) != 0){
            whereSql = " where "+whereSql;
        }


        String totalRowSql = "select count(*) from "+this.tableName+" "+replaceOrderBy(whereSql);
        int totalRow = jdbcTemplate.queryForObject(totalRowSql,args,Integer.class);
        int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;
        whereSql += " limit "+(whichPage-1) * pageSize+","+pageSize;
        List<T> pageList = this.findEntity("select * from "+this.tableName+" "+whereSql,args);

        Page<T> page = new Page<>(pageList, whichPage, pageSize, totalPage, totalRow);


        return page;

    }

    public Page<Map<String,Object>> paginate(int whichPage,int pageSize,String select,String sql,Object ... args){

        String totalRowSql = "select count(*) "+replaceOrderBy(sql);
        int totalRow = jdbcTemplate.queryForObject(totalRowSql,args,Integer.class);
        int totalPage = totalRow%pageSize == 0 ? totalRow/pageSize : totalRow/pageSize+1;

        sql += " limit "+(whichPage-1) * pageSize+","+pageSize;
        List<Map<String,Object>> pageList = this.select(select +" "+ sql,args);

        Page<Map<String,Object>> page = new Page<>(pageList, whichPage, pageSize, totalPage, totalRow);


        return page;

    }

    // 组装SQL
    private String makeSql(String sqlFlag,T entity) {
        StringBuffer sql = new StringBuffer();
        StringBuffer valSql = new StringBuffer();


        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + this.tableName);
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {


                try {
                    // 暴力反射
                    fields[i].setAccessible(true);
                    //属性是否为空
                    if(null != fields[i].get(entity)){
                        String column = fields[i].getAnnotation(Column.class).name();
                        sql.append(column).append(",");
                        valSql.append("?,");
                    }
                } catch (Exception e) {
                    log.error("组装INSERT SQL时反射异常");
                    e.printStackTrace();
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            sql.append(valSql.deleteCharAt(valSql.length()-1));
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            sql.append(" UPDATE " + this.tableName + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    //属性是否为空
                    if(null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)){
                        String column = fields[i].getAnnotation(Column.class).name();
                        sql.append(column).append("=").append("?,");
                    }
                } catch (Exception e) {
                    log.error("组装UPDATE SQL语句反射异常");
                    e.printStackTrace();
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE "+this.pkName+"=?");
        } else if (sqlFlag.equals(SQL_DELETE)) {

            sql.append(" DELETE FROM " + this.tableName + " WHERE "+this.pkName+"=?");
        }
        System.out.println("SQL=" + sql);
        return sql.toString();

    }

    // 设置参数
    private Object[] setArgs(T entity, String sqlFlag) {
        Field[] fields = entityClass.getDeclaredFields();
        List<Object> args = new ArrayList<>();
        if (sqlFlag.equals(SQL_INSERT)) {
            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射

                    if(null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)){
                        args.add(fields[i].get(entity));
                    }

                } catch (Exception e) {
                    log.error("设置INSERT参数时反射异常");
                    e.printStackTrace();

                }
            }

        } else if (sqlFlag.equals(SQL_UPDATE)) {
            Object id = null;
            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    if(null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)){
                        if(fields[i].getAnnotation(Column.class).isPK()){
                            id = fields[i].get(entity);
                        }
                        args.add(fields[i].get(entity));
                    }

                } catch (Exception e) {
                    log.error("设置UPDATE参数时反射异常");
                    e.printStackTrace();
                }
            }
            args.add(id);

        } else if (sqlFlag.equals(SQL_DELETE)) {
            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    if(null != fields[i].get(entity)){
                        if(fields[i].isAnnotationPresent(Column.class) && fields[i].getAnnotation(Column.class).isPK()){
                            args.add(fields[i].get(entity));
                            break;
                        }
                    }

                } catch (Exception e) {
                    log.error("设置DELETE参数时反射异常");
                    e.printStackTrace();
                }
            }
        }
        return args.toArray();

    }


    /**
     * 获取主键
     * @return
     */
    private void getTablePkColumn(){
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0;i < fields.length; i++) {
            try {
                fields[i].setAccessible(true); // 暴力反射

                if (fields[i].isAnnotationPresent(Column.class)) {
                    if (fields[i].getAnnotation(Column.class).isPK()) {
                        pkName = fields[i].getAnnotation(Column.class).name();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取主键时反射异常");
            }
        }
    }

    /**
     * 过滤order
     * @param sql
     * @return
     */
    private String replaceOrderBy(String sql){
        return ORDER_BY_PATTERN.matcher(sql).replaceAll("");
    }

    public static void main(String [] args){

        String sss = "WHERE IS";

       // System.out.println(sss.toLowerCase().indexOf("WHERE"));
    }




}



