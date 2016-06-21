package org.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Register extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String url = request.getParameter("url");
            out.println("<h3>"+url+"</h3>");
            JSONObject obj = new JSONObject();
            Document doc = Jsoup.connect(url).get();
            
            
            String keywords = doc.select("meta[name=keywords]").attr("content"),
                   description = doc.select("meta[name=description]").attr("content"),
                   title = doc.select("title").text();           
            
            obj.put("keywords", keywords);
            obj.put("description", description);
            
            List<String> imgPath = new ArrayList<>();
            
            
            Elements img = doc.getElementsByTag("img");
            for (Element object : img) {
                imgPath.add(object.attr("src"));
            }
            obj.put("imgPath", imgPath);
            out.println("<p><strong>Title: </strong>"+title+"</p><p><strong>Keywords: </strong><i>"+keywords+"</i></p><p><strong>Description: </strong>"+description+"</p>");
            for (String z : imgPath) {
                out.println("<img src="+z+">");
            }
            
            
            StringWriter outt = new StringWriter();
            obj.writeJSONString(outt);
            String jsonText = outt.toString();

            out.println("<p>"+jsonText+"</p>");
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
