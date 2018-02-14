/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import com.dyfferential.vyral.common.logger.Logger;
import com.dyfferential.vyral.web.util.DateFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import net.crowninteractive.wfmworker.service.Awesome;

/**
 *
 * @author osita
 */
public class Extension {

    protected final Logger logger = Logger.getLogger(getClass());

    protected void log(String operationName, String msg) {
        System.out.println("--log " + operationName + ":-  " + msg);
    }

    protected void trail(String accountlogin, String remoteAddr) {
        System.out.println(accountlogin + " --- " + DateFormatter.getSimpleDateTime(DateFormatter.getCurrentDateTime2()) + " --- " + remoteAddr);
    }

    protected String process(Awesome awe, HttpServletRequest request) {
        return process(awe, request, null);
    }

    protected String getRemoteAddr(HttpServletRequest request) {
        return request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For") : "localhost";
    }

    protected String process(Awesome awe, HttpServletRequest request, Object o) {
        System.out.println("... headers ...");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String nextElement = headers.nextElement();
            System.out.printf("%s : %s\n", nextElement, request.getHeader(nextElement));
        }
        System.out.println("... parameters ...");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String nextElement = params.nextElement();
            System.out.printf("%s : %s\n", nextElement, request.getParameter(nextElement));
        }
        if (o != null) {
            System.out.println(new Gson().toJson(o));
        }
        System.out.println("... response ...");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
        String js = gson.toJson(awe);
        logger.info(String.format("request from %s @ endpoint %s complete. Returning: %s", getRemoteAddr(request), request.getRequestURI(), js));
        return js;
    }

  
}
