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
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class FullController implements MyController {
    @Autowired
    ShortenLinkService shortenLinkService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String shortLink = request.getParameter("short");
        PrintWriter writer = response.getWriter();
        if (shortLink == null) {
            writer.println("Please input 'short' parameter");
            writer.flush();
        } else {
            printFullLink(shortLink, writer);
        }
    }

    private void printFullLink(String shortLink, PrintWriter writer) {
        Optional<Link> optionalLink = shortenLinkService.fullLink(Link.linkTo(shortLink));
        try {
            Link link = optionalLink.orElseThrow(NoSuchElementException::new);
            writer.println("Full link : " + link.link());
        } catch (NoSuchElementException e) {
            writer.println("Full link by passed short link not found");
            writer.flush();
        }
    }

}
