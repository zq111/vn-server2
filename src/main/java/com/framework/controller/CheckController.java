package com.framework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Statement;

/**
 * Created by victor on 6/5/17.
 */
@Slf4j
@Controller
@RequestMapping(value = "/soso")
public class CheckController {

    @Autowired
    DataSource druidDataSource;

    @ResponseBody
    @RequestMapping("/check")
    public String check() {
        return "ok";
    }


    @RequestMapping("/status")
    public ModelAndView status() {
        ModelAndView modelAndView = new ModelAndView("status");

        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        try {
            Statement statement = druidDataSource.getConnection().createStatement();
            statement.execute("create table SERVER_STATE(id bigint,server_ip varchar(60),server_load bigint,server_method varchar(60), server_port  varchar(10), server_pass varchar(255),primary key (id))");
            statement.close();
        }
        catch (Exception e){
            log.error("init", e);
        }
        return "ok";
    }
}
