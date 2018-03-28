package com.king.dao;

import com.king.entity.XkEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by 金丹 on 2017/12/19.
 */
@Repository
public interface XkEntityDao {

    @Select("select p.cardType    as type, " +
            "       p.cardno      as cardNo, " +
            "       p.requestDate as times, " +
            "       c.shortname   as customerName " +
            "  from DX_PAYMENTPROCESS p, DX_CUSTOMER c " +
            " where p.requestDate >= TO_DATE('2017/12/10 00:00:00', 'yyyy-MM-dd hh24:mi:ss') " +
            "   and p.requestDate <= TO_DATE('2017/12/18 23:59:59', 'yyyy-MM-dd hh24:mi:ss') " +
            "   and p.customernumber = c. customernumber  " +
            "   and p.customernumber <> '4001001708' " +
            "   and p.cardno=#{cardNo}")
    List<XkEntity> queryXkEntityByNo(@Param("cardNo") String cardNo);
}
