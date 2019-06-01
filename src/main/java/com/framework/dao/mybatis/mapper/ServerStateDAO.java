package com.framework.dao.mybatis.mapper;

import com.framework.dao.mybatis.model.ServerState;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ServerStateDAO {

    @Update("update server_state set server_load=server_load+#{val} where server_ip=#{server_ip}")
    void addLoad(@Param("val") int val, @Param("server_ip") String serverIp);

    @Select("select id, server_ip as serverIp,server_method as serverMethod, server_pass as serverPass ,server_port as serverPort,server_load as serverLoad  from server_state")
    List<ServerState> listAll();

    @Insert("insert into server_state (id,server_ip,server_method,server_pass,server_port,server_load) values (#{id},#{serverIp},#{serverMethod},#{serverPass},#{serverPort},0)")
    void add(ServerState serverState);
}
