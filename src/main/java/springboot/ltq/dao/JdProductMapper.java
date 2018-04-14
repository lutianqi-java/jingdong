package springboot.ltq.dao;

import springboot.ltq.entity.JdProduct;

public interface JdProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JdProduct record);

    int insertSelective(JdProduct record);

    JdProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JdProduct record);

    int updateByPrimaryKey(JdProduct record);
}