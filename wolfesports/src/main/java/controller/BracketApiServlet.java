package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Bracket;
import service.BracketService;
import util.BracketExclusionStrategy;

public class BracketApiServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
    // ServletException, IOException {

    // Gson gson = new GsonBuilder()
    // .setExclusionStrategies(new TeamExclusionStrategy(), new
    // BracketExclusionStrategy()).create();
    // String pathInfo = req.getPathInfo();
    // String jsonResponse = null;
    // BracketService brackettService = applicationContext.getBean("bracketService",
    // BracketService.class);

    // if (pathInfo != null && pathInfo.length() > 1) {
    // String bracketIdStr = pathInfo.substring(1);
    // Long bracketId;

    // try {
    // bracketId = Long.parseLong(bracketIdStr);
    // } catch (NumberFormatException e) {
    // resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    // jsonResponse = gson.toJson("Invalid player ID format.");
    // resp.setContentType("application/json");
    // resp.setCharacterEncoding("UTF-8");
    // try (PrintWriter out = resp.getWriter()) {
    // out.print(jsonResponse);
    // out.flush();
    // }
    // return;
    // }

    // Bracket bracket = brackettService.getBracket(bracketId).orElse(null);
    // jsonResponse = gson.toJson(bracket);

    // } else {
    // resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    // return;
    // }

    // resp.setContentType("application/json");
    // resp.setCharacterEncoding("UTF-8");

    // try (PrintWriter out = resp.getWriter()) {
    // out.print(jsonResponse);
    // out.flush();
    // }
    // }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new BracketExclusionStrategy()).create();
        String pathInfo = req.getPathInfo();
        String jsonResponse = null;
        BracketService brackettService = applicationContext.getBean("bracketService", BracketService.class);

        if (pathInfo != null && pathInfo.length() > 1) {
            String bracketIdStr = pathInfo.substring(1);
            Long bracketId;

            try {
                bracketId = Long.parseLong(bracketIdStr);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = gson.toJson("Invalid player ID format.");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                try (PrintWriter out = resp.getWriter()) {
                    out.print(jsonResponse);
                    out.flush();
                }
                return;
            }

            Bracket bracket = brackettService.getBracket(bracketId).orElse(null);
            jsonResponse = gson.toJson(bracket);

        } else {
            resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }
}
