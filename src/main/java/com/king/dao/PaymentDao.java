package com.king.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 金丹
 * @since 2018/1/10.
 */
@Repository
public interface PaymentDao {

    @Select("select id\n" +
            "  from DX_PAYMENT\n" +
            " where finishDate >= to_date('2018/1/10 18:50:00', 'yyyy-mm-dd hh24:mi:ss')\n" +
            " and finishDate <= to_date('2018/1/10 19:30:00', 'yyyy-mm-dd hh24:mi:ss') " +
            "   and customerNumber = 4001001708\n" +
            "   and status in('SUCCESS','FAILED')")
    List<String> queryRequestId();
}
