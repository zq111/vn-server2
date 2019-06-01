package com.framework.controller;

import com.alibaba.fastjson.JSON;
import com.framework.dao.mybatis.model.ServerState;
import com.framework.dao.mybatis.mapper.ServerStateDAO;
import com.framework.result.Result;
import com.framework.result.ServerIpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/soso/server")
public class ServerController {

    @Autowired
    private ServerStateDAO serverStateDAO;


    @ResponseBody
    @RequestMapping("/ip")
    public String getServerIp(){
        Result result = new Result();
        try {
            List<ServerState> serverStates = serverStateDAO.listAll();
            ServerState target = null;
            int max = Integer.MAX_VALUE;
            for(ServerState serverState : serverStates) {
                if (serverState.getServerLoad() < max) {
                    max = serverState.getServerLoad();
                    target = serverState;
                }
            }
            ServerIpResult serverIpResult = new ServerIpResult();
            serverIpResult.setIp(target.getServerIp());
            serverIpResult.setMethod(target.getServerMethod());
            serverIpResult.setPass(target.getServerPass());
            serverIpResult.setPort(target.getServerPort());
            result.setStatus(1);
            result.setData(serverIpResult);
        }
        catch (Exception e){
           log.error("getServerIp error - ", e);
        }

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("/connect")
    public String connect(String serverIp){
        Result result = new Result();
        try {
            serverStateDAO.addLoad(1, serverIp);
            result.setStatus(1);
        }
        catch (Exception e){
            log.error("connect update error", e);
        }
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/disconnect")
    public String disconnect(String serverIp){
        Result result = new Result();
        try {
            serverStateDAO.addLoad(-1, serverIp);
            result.setStatus(1);
        }
        catch (Exception e){
            log.error("disconnect update error", e);
        }
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ServerState serverState){
        try {
            serverStateDAO.add(serverState);
        }
        catch (Exception e){
            log.error("disconnect update error", e);
        }
        return "ok";
    }
}
