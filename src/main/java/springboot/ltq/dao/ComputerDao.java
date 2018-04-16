package springboot.ltq.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot.ltq.entity.Computer;

import java.util.List;
import java.util.Map;

@Repository
public interface ComputerDao {

    void inserComputerList(@Param("computerList") List<Computer> computerList);

    List<Computer> list(Map<String, Object> map);
}
