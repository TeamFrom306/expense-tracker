package org.university.innopolis.server.services.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.university.innopolis.server.model.Holder;
import org.university.innopolis.server.persistence.HolderRepository;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.services.TokenService;
import org.university.innopolis.server.services.exceptions.BadCredentialsException;
import org.university.innopolis.server.services.exceptions.DuplicatedUserException;
import org.university.innopolis.server.services.realization.helpers.CredentialValidator;
import org.university.innopolis.server.services.realization.mappers.HolderMapper;
import org.university.innopolis.server.services.realization.helpers.EncoderService;
import org.university.innopolis.server.views.HolderView;

import java.util.Objects;

@Component
public class AuthService implements AuthenticationService {
    private HolderRepository holderRepository;
    private TokenService tokenService;
    private EncoderService shaEncoder;

    @Autowired
    public AuthService(HolderRepository holderRepository,
                       TokenService tokenService,
                       EncoderService shaEncoder) {
        this.holderRepository = holderRepository;
        this.tokenService = tokenService;
        this.shaEncoder = shaEncoder;
    }

    @Override
    public int getHolderId(String token) {
        if (token == null)
            return -1;
        Holder holder = holderRepository.getByToken(token);
        return holder == null ? -1 : holder.getId();
    }

    @Override
    public void revokeTokenById(int id) {
        Holder holder = holderRepository.getById(id);
        if (holder != null) {
            holder.setToken(null);
            holderRepository.save(holder);
        }
    }


    @Override
    public HolderView registerHolder(String login, String password) throws
            DuplicatedUserException,
            BadCredentialsException {
        CredentialValidator.validateCredentials(login, password);

        final String encodedPassword = shaEncoder.getHash(password);

        if (containsLogin(login))
            throw new DuplicatedUserException(login);

        Holder res = holderRepository.save(new Holder(login, encodedPassword));
        return HolderMapper.map(res);
    }

    @Override
    public HolderView getAuthentication(String login, String password) throws BadCredentialsException {
        CredentialValidator.validateCredentials(login, password);

        final String encodedPassword = shaEncoder.getHash(password);

        Holder holder = holderRepository.getByLogin(login);
        if (holder == null || !checkPasswords(encodedPassword, holder))
            throw new BadCredentialsException();
        if (holder.getToken() == null)
            storeToken(holder, login);
        return HolderMapper.map(holder);
    }

    @Override
    public boolean isAuthorized(int holderId, String login) {
        Holder holder = holderRepository.getByIdAndLogin(holderId, login);
        return holder != null;
    }

    @Override
    public void revokeToken(String token) {
        holderRepository.setTokenNull(token);
    }

    private boolean containsLogin(String login) {
        return holderRepository.getByLogin(login) != null;
    }

    private boolean checkPasswords(String password, Holder holder) {
        return Objects.equals(holder.getPassword(), password);
    }

    private void storeToken(Holder holder, String login) {
        String token = tokenService.generateToken(login, holder.getId());
        holder.setToken(token);
        holderRepository.save(holder);
    }
}
