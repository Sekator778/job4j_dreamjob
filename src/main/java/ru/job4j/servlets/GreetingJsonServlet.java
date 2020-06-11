package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * https://www.java67.com/2015/02/how-to-parse-json-tofrom-java-object.html
 */
public class GreetingJsonServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        String name = "Nice to meet you, ";
        name = name + req.getParameter("name");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(name);
        writer.append(json);
        writer.flush();
    }
}
