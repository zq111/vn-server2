package com.framework.utils;

import com.framework.dao.mybatis.model.SystemRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author binhao.guo
 * @version 1.0
 * @Date 2018/12/3
 */
@Slf4j
public class ApiRecordQuery {
    private ApiRecordQuery() {}

    private static final BlockingQueue<SystemRecord> query = new ArrayBlockingQueue<>(500);

    public static void putRecord(SystemRecord record) {
        query.add(record);
    }

    public static SystemRecord getRecord() {
        /**
         *  add        增加一个元索                  如果队列已满，则抛出一个IIIegaISlabEepeplian异常
         *  remove     移除并返回队列头部的元素        如果队列为空，则抛出一个NoSuchElementException异常
         *  element    返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
         *  offer      添加一个元素并返回true         如果队列已满，则返回false
         *  poll       移除并返问队列头部的元素        如果队列为空，则返回null
         *  peek       返回队列头部的元素             如果队列为空，则返回null
         *  put        添加一个元素                  如果队列满，则阻塞
         *  take       移除并返回队列头部的元素        如果队列为空，则阻塞
         */
        return query.poll();
    }

}
