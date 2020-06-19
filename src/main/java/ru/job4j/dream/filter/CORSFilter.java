package ru.job4j.dream.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Access-Control-Allow-Origin : указывает авторизованные домены для выполнения междоменного запроса.
 * Используйте «*» в качестве значения, если нет ограничений.
 * Access-Control-Allow-Credentials : указывает, могут ли междоменные запросы иметь учетные данные авторизации или нет.
 * Access-Control-Expose-Headers : указывает, какие заголовки безопасны для показа.
 * Access-Control-Max-Age : указывает, как долго могут кэшироваться результаты предварительного запроса.
 * Access-Control-Allow-Methods : указывает методы, разрешенные при доступе к ресурсу.
 * Access-Control-Allow-Headers : указывает, какие имена полей заголовков можно использовать во время фактического запроса.
 */
public class CORSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}