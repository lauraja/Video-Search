import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

import ir.SearchWeb;

public class Main {

    public static void main(String[] args) {

        port(Integer.valueOf(System.getenv("PORT")));
        staticFileLocation("/public");

        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String q = request.queryParams("q");

            if(q != null) {
                SearchWeb sw = new SearchWeb(q);
                return new ModelAndView(attributes, "response.ftl");
            } else {
                return new ModelAndView(attributes, "index.ftl");
            }
        }, new FreeMarkerEngine());

    }

}
