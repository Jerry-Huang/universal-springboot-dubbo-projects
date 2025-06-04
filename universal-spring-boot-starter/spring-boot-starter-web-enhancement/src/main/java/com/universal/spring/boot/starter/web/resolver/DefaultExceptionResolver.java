package com.universal.spring.boot.starter.web.resolver;

import com.universal.spring.boot.metadata.Environment;
import com.universal.spring.boot.metadata.exception.Code;
import com.universal.spring.boot.metadata.exception.SystemException;
import com.universal.spring.boot.metadata.message.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;


@ControllerAdvice
public class DefaultExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(DefaultExceptionResolver.class);

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse resolveException(NativeWebRequest httpRequest, Exception exception) {

        logger.error(Code.SYS_SERVICE_ERROR.toString(), exception);

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(Code.SYS_SERVICE_ERROR.getCode());
        apiResponse.setMessage(Code.SYS_SERVICE_ERROR.getText());

        if (Environment.current() != Environment.PRD) {
            apiResponse.setDebug(ExceptionUtils.getStackTrace(exception));
        }

        return apiResponse;
    }

    @ExceptionHandler({SystemException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse resolveUniversalException(NativeWebRequest httpRequest, SystemException exception) {

        logger.error(exception.getCode().toString(), exception);

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(exception.getCode().getCode());
        apiResponse.setMessage(exception.getMessage());

        if (Environment.current() != Environment.PRD) {
            if (StringUtils.isNotBlank(exception.getDebug())) {
                apiResponse.setDebug(exception.getDebug());
            } else {
                apiResponse.setDebug(ExceptionUtils.getStackTrace(exception));
            }
        }

        return apiResponse;
    }

}
