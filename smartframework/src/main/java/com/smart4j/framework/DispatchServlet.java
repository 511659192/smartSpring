package com.smart4j.framework;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.smart4j.framework.bean.Data;
import com.smart4j.framework.bean.Hander;
import com.smart4j.framework.bean.Param;
import com.smart4j.framework.bean.View;
import com.smart4j.framework.helper.BeanHelper;
import com.smart4j.framework.helper.ConfigHelper;
import com.smart4j.framework.helper.ControllerHelper;
import com.smart4j.framework.helper.HelperLoader;
import com.smart4j.framework.util.CodecUtil;
import com.smart4j.framework.util.JsonUtil;
import com.smart4j.framework.util.ReflectionUtil;
import com.smart4j.framework.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by ym on 2018/5/11.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {

    public void init(ServletConfig servletConfig) {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        Hander hander = ControllerHelper.getHander(requestMethod, requestPath);
        if (hander != null) {
            Class<?> controllerClass = hander.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paramMap = Maps.newHashMap();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            String requestBody = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotBlank(requestBody)) {
                List<String> urlParamList = Splitter.on("&").omitEmptyStrings().splitToList(requestBody);
                for (String param : urlParamList) {
                    List<String> entry = Splitter.on("=").splitToList(param);
                    paramMap.put(entry.get(0), entry.get(1));
                }
            }

            Param param = new Param(paramMap);
            Method actionMethod = hander.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JsonUtil.toJosn(model));
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
