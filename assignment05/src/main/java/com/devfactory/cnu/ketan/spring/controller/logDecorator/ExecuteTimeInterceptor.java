package com.devfactory.cnu.ketan.spring.controller.logDecorator;

/**
 * Created by ketanpatil on 11/07/16.
 */
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devfactory.cnu.ketan.spring.controller.model.LogJson;

import com.devfactory.cnu.ketan.spring.controller.queue.SimpleQueueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(ExecuteTimeInterceptor.class);

    LogJson log ;

    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    //after the handler is executed
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        String url = request.getRequestURI();
        String data = "";
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            data.concat(paramName);
            data.concat(": ");
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                data.concat(paramValue);
                data.concat(", ");
            }
        }
        String paramNames = data;
        int responceCode = response.getStatus();
        String ipAddress = request.getRemoteAddr();

        LogJson log = new LogJson(endTime,url,paramNames,responceCode,ipAddress);

        String jsonInString;

        jsonInString = mapper.writeValueAsString(log);


        super.postHandle(request, response, handler, modelAndView);
        SimpleQueueService awsQueueService = new SimpleQueueService();
        awsQueueService.sendMessage(jsonInString);

        //log it
        if (logger.isDebugEnabled()) {

            String message;
            logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");


            //return message;

        }
    }
}