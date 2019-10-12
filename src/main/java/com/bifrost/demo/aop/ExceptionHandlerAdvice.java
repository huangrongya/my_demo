/**
 * Copyright (C) 2017 - 2020 https://github.com/joewee
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bifrost.demo.aop;

import com.bifrost.demo.exception.KnownRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author royle.huang
 * @Date 18:29 2019/10/11
 * @Description 全局异常处理
 **/
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private static String ERROR_OCCURES = "发生错误";
    private static String LACK_ARGS = "缺少参数";

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map exceptionHandler(Exception ex) throws Exception {
        Map map = new HashMap(2);
        if (ex instanceof MissingServletRequestParameterException) {
            map.put("code", 500);
            map.put("msg", LACK_ARGS + "：" + ex.getMessage());
        } else if (ex instanceof BindException) {
            List<FieldError> errors = ((BindException) ex).getFieldErrors();
            Map errorMap = new HashMap(5);
            map.put("code", 400);
            for (FieldError fieldError : errors) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            map.put("msg", errorMap);
            return map;
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<ObjectError> errors = bindingResult.getAllErrors();
            map.put("code", 400);
            map.put("msg", errors.get(0).getDefaultMessage());
            return map;
        }else if (ex instanceof ConstraintViolationException) {
            StringBuilder stringBuilder = new StringBuilder();
            ConstraintViolationException exs = (ConstraintViolationException) ex;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                /**打印验证不通过的信息*/
                stringBuilder.append(item.getMessage()).append("  ");
            }
            map.put("code", 400);
            map.put("msg", stringBuilder.toString());
        } else if (ex instanceof IllegalArgumentException) {
            map.put("code", 400);
            map.put("msg", ex.getMessage());
            logger.error(ex.getMessage(), ex);
        } else if (ex instanceof KnownRuntimeException) {
            map.put("code", 400);
            map.put("msg", ex.getMessage());
            logger.error(ex.getMessage(), ex);
        } else {
            map.put("code", 500);
            map.put("msg", ERROR_OCCURES);
            logger.error(ex.getMessage(), ex);
        }
        return map;
    }
}
