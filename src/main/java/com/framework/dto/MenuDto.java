package com.framework.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

	private String uuid;

	private String menu_name;

	private String parent_uuid;

	private String menu_url;

	private String menu_icon;

	private String menu_remark;

	private Integer sort_num;

	private Integer level;

	private String is_leaf;

	private List<MenuDto> children;

}
