package com.framework.service.admin;

import com.framework.bean.response.CommonResponse;
import java.util.List;


public interface AdminSystemMenuService {

    List<CommonResponse> getAllMenu(String reqJson) throws Exception;

    List<CommonResponse> getTopMenus(String uuid) throws Exception;

    List<CommonResponse> getMenuByParaentUuid(String parentUuid,String userUuid) throws Exception;

    List<CommonResponse> addMenu(String reqJson) throws Exception;

    List<CommonResponse> deleteMenu(String reqJson) throws Exception;

    List<CommonResponse> sortMenu(String reqJson) throws Exception;

    List<CommonResponse> getMenuByUserUUID(String reqJson) throws Exception;

    CommonResponse getMenuByUUID(String reqJson) throws Exception;
}
    