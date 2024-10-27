package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Tournament;
import service.TournamentService;
import util.BracketExclusionStrategy;
import util.GameExclusionStrategy;
import util.TeamExclusionStrategy;
import util.TournamentExclusionStrategy;

public class TournamentApiServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String jsonResponse = null;
        TournamentService tournamentService = applicationContext.getBean("tournamentService", TournamentService.class);

        if (pathInfo != null && pathInfo.length() > 1) {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new TeamExclusionStrategy(), new BracketExclusionStrategy(),
                            new GameExclusionStrategy())
                    .create();
            String tournamentIdStr = pathInfo.substring(1);
            Long tournamentId;

            try {
                tournamentId = Long.parseLong(tournamentIdStr);
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

            Tournament tournament = tournamentService.getTournament(tournamentId).orElse(null);
            jsonResponse = gson.toJson(tournament);

        } else {
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new GameExclusionStrategy(), new TournamentExclusionStrategy())
                    .create();
            List<Tournament> tournaments = tournamentService.getAllTournaments();

            jsonResponse = gson.toJson(tournaments);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson("post");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson("put");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson("patch");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson("delete");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }
}
