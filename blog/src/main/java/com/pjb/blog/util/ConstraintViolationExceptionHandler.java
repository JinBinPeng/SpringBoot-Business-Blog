package com.pjb.blog.util;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ConstraintViolationExceptionHandler {
	private ConstraintViolationExceptionHandler() {
		throw new IllegalStateException("Utility class");
	}
	/**
	 * 获取批量异常信息
	 */
	public static String getMessage(ConstraintViolationException e) {
		List<String> msgList = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
			msgList.add(constraintViolation.getMessage());
        }
		return StringUtils.join(msgList.toArray(), ";");
	}
}
