package com.framework.utils.jwt;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.ToString;

/**
 * 验证信息
 */
@ToString
@Data
public class CheckResult {
	private int errCode;

	private boolean success;

	private Claims claims;
}
