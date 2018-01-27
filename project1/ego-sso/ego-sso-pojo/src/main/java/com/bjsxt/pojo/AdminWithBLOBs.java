package com.bjsxt.pojo;

import java.io.Serializable;

public class AdminWithBLOBs extends Admin implements Serializable {
    /** 权限  nav_list **/
    private String navList;

    /** todolist  todolist **/
    private String todolist;

    /**   tableName: t_admin   **/
    private static final long serialVersionUID = 1L;

    /**   权限  nav_list   **/
    public String getNavList() {
        return navList;
    }

    /**   权限  nav_list   **/
    public void setNavList(String navList) {
        this.navList = navList == null ? null : navList.trim();
    }

    /**   todolist  todolist   **/
    public String getTodolist() {
        return todolist;
    }

    /**   todolist  todolist   **/
    public void setTodolist(String todolist) {
        this.todolist = todolist == null ? null : todolist.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", navList=").append(navList);
        sb.append(", todolist=").append(todolist);
        sb.append("]");
        return sb.toString();
    }
}