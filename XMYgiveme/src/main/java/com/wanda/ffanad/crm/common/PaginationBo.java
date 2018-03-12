/**
 * 分页公共Vo
 */
package com.wanda.ffanad.crm.common;

import java.util.List;

/**
 * @author 姜涛
 *
 */
public class PaginationBo<E> {

    private Integer total;
    private List<E> rows;

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the rows
     */
    public List<E> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(List<E> rows) {
        this.rows = rows;
    }

}
