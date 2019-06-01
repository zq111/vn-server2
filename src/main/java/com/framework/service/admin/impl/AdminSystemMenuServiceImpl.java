package com.framework.service.admin.impl;

import com.framework.bean.response.CommonResponse;
import com.framework.dto.MenuDto;
import com.framework.service.admin.AdminSystemMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminSystemMenuServiceImpl implements AdminSystemMenuService {


    @Override
    public List<CommonResponse> getAllMenu(String reqJson) throws Exception {
//        BaseRequest req = GsonTools.str2T(reqJson,BaseRequest.class);
//
//        List<MenuDto> topMenu = this.getTopMenus(req.getUuid());
//        findChildren(topMenu, req.getUuid());
//        return topMenu;
        return null;
    }

    @Override
    public List<CommonResponse> getTopMenus(String uuid) throws Exception {
        return null;
    }

    @Override
    public List<CommonResponse> getMenuByParaentUuid(String parentUuid, String userUuid) throws Exception {
       return null;
    }

    private void findChildren(List<MenuDto> menu_list, String user_uuid) throws Exception{
//        for (MenuDto menu : menu_list) {
//            List<MenuDto> child_menu_list = this.getMenuByParaentUuid(menu.getUuid(), user_uuid);
//            if (null != child_menu_list && !child_menu_list.isEmpty()){
//                findChildren(child_menu_list, user_uuid);
//                menu.setChildren(child_menu_list);
//            }
//        }
    }

    @Override
    public List<CommonResponse> addMenu(String reqJson) throws Exception {
        return null;
    }

    @Override
    public List<CommonResponse> deleteMenu(String reqJson) throws Exception {
        return null;
    }

    @Override
    public List<CommonResponse> sortMenu(String reqJson) throws Exception {
        return null;
    }

    @Override
    public List getMenuByUserUUID(String reqJson) throws Exception {
        return null;
    }

    @Override
    public CommonResponse getMenuByUUID(String reqJson) throws Exception {
        return null;
    }
}
