package springboot.ltq.dao;

import springboot.ltq.entity.JdOrderPrice;

public interface JdOrderPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JdOrderPrice record);

    int insertSelective(JdOrderPrice record);

    JdOrderPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JdOrderPrice record);

    int updateByPrimaryKey(JdOrderPrice record);
}