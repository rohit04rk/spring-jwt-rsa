package com.mb.common.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomErrorCode;
import com.mb.common.exception.CustomException;

@Component
public class Mapper {
	
	@Autowired
	private Environment env;

	/**
	 * Map source object to target class
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <T>
	 * @param srcObj
	 * @param targetClass
	 * @return T class object
	 */
	public <T> T convert(Object srcObj, Class<T> targetClass) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		try {
			return modelMapper.map(srcObj, targetClass);
		} catch (Exception e) {
			throw new CustomException(env.getProperty(ExceptionMessage.INTERNAL_SERVER_ERROR), e,
					CustomErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Map source object list to target class list
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param <S>
	 * @param <T>
	 * @param srcList
	 * @param targetClass
	 * @return T class objects list
	 */
	public <S, T> List<T> convertToList(List<S> srcList, Class<T> targetClass) {
		List<T> response = new ArrayList<>();
		srcList.stream().forEach(source -> response.add(convert(response, targetClass)));

		return response;
	}

}
