package com.training.greetapi.shared.security.repository;

import com.training.greetapi.shared.security.entity.SecurityCsrfToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomCsrfTokenRepository.class);

    private final JPACSRFTokenRepository csrfTokenRepository;

    public CustomCsrfTokenRepository(JPACSRFTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        logger.info("at CustomCsrfTokenRepository->generateToken" + "token :" + uuid);
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf_token", uuid);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {


        String userId = request.getHeader("X-USER-ID");
        logger.info("CustomCsrfTokenRepository->saveToken" + " user-id :" + userId);

        Optional<SecurityCsrfToken> entity = csrfTokenRepository.findTokenByUserIdentifier(userId);
        SecurityCsrfToken csrfToken = null;
        if (entity.isPresent()) {
            csrfToken = entity.get();
            csrfToken.setToken(token.getToken());
            logger.info("CustomCsrfTokenRepository->saveToken" + "updating :" + "token :" + token.getToken());
        } else {
            csrfToken = new SecurityCsrfToken();
            csrfToken.setToken(token.getToken());
            csrfToken.setUserIdentifier(userId);
            logger.info("CustomCsrfTokenRepository->saveToken" + "saving :" + "token :" + token.getToken());

        }
        csrfTokenRepository.save(csrfToken);

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Optional<SecurityCsrfToken> entity = csrfTokenRepository.findTokenByUserIdentifier(userId);
        SecurityCsrfToken csrfToken = null;
        if (entity.isPresent()) {
            csrfToken = entity.get();
            logger.info("CustomCsrfTokenRepository->loadToken" + "token : " + csrfToken.getToken());

            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf_token", csrfToken.getToken());
        }
        logger.info("CustomCsrfTokenRepository->loadToken" + "no such user id");

        return null;
    }
}
