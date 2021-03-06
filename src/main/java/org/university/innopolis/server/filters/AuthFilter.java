package org.university.innopolis.server.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.TokenService;
import org.university.innopolis.server.services.exceptions.CorruptedTokenException;
import org.university.innopolis.server.services.exceptions.ExpiredTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {
    private static final String TOKEN_HEADER = "Authorization";
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final TokenService tokenService;
    private AuthenticationService holderService;

    @Autowired
    public AuthFilter(TokenService tokenService,
                      AuthenticationService holderService) {
        this.tokenService = tokenService;
        this.holderService = holderService;
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.sendError(401, message);
    }

    private boolean isValid(HttpServletResponse response, String token) throws IOException {
        try {
            tokenService.validateToken(token);
            return true;
        } catch (ExpiredTokenException e) {
            sendError(response, e.getMessage());
            logger.debug("Token validation, msg: {}", e.getMessage());
            return false;
        } catch (CorruptedTokenException e) {
            sendError(response, "Token is corrupted");
            logger.debug("Token validation, msg: {}", "Token is corrupted");
            return false;
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws
            ServletException,
            IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader(TOKEN_HEADER);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (authHeader == null) {
            sendError(response, "Missing Authorization header");
            logger.debug("{}, msg: {}", ((HttpServletRequest) req).getRequestURI(), "Missing Authorization header");
            return;
        } else {
            final String token = authHeader.substring(7);
            if (!isValid(response, token)) {
                holderService.revokeToken(token);
                return;
            }

            int holderId = holderService.getHolderId(token);
            if (holderId == -1) {
                sendError(response, "Invalid token");
                logger.debug("{}, msg: {}", ((HttpServletRequest) req).getRequestURI(), "Invalid token");
                return;
            }
            request.setAttribute("holderId", holderId);
        }

        chain.doFilter(req, res);
    }
}
