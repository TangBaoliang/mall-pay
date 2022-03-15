package ltd.itlover.ltd.pay.mapper;


import ltd.itlover.ltd.pay.pojo.PayInfo;
import ltd.itlover.ltd.pay.pojo.PayInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInfoMapper {
    long countByExample(PayInfoExample example);

    int deleteByExample(PayInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    List<PayInfo> selectByExample(PayInfoExample example);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    int updateByExample(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

}