package shorter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shorter.model.Link;
import shorter.service.ShortenLinkService;
import web.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ShorterController implements MyController {
    @Autowired
    ShortenLinkService shortenLinkService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullLink = request.getParameter("full");
        PrintWriter writer = response.getWriter();
        if (fullLink == null) {
            writer.println("Please input 'full' parameter");
            writer.flush();
        } else {
            printShortLink(fullLink, writer);
        }
    }

    private void printShortLink(String fullLink, PrintWriter writer) {
        Link link = shortenLinkService.shortLink(Link.linkTo(fullLink));
        writer.println("Generated short link : " + link.link());
        writer.flush();
    }
}
