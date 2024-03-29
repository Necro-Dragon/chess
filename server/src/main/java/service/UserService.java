package service;

import RequestClasses.LoginRequest;
import RequestClasses.LogoutRequest;
import RequestClasses.RegisterRequest;
import ResultClasses.LoginResult;
import ResultClasses.RegisterResult;
import Exceptions.*;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class UserService {
    AuthDAO authDAO;
    UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
    public RegisterResult register(RegisterRequest request) throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
        UserData myUserData = new UserData(request.username(), request.password(), request.email());
        AuthData myAuthData = new AuthData(authDAO.generateAuthToken(myUserData.username()), myUserData.username());
        if (myUserData.username() == null || myUserData.password() == null || myUserData.email() == null){
            throw new BadRequestException();
        }
        UserData retrievedUserData = userDAO.getUserData(myUserData.username());
        if (retrievedUserData != null) {
            if (Objects.equals(retrievedUserData.username(), myUserData.username())){
                throw new AlreadyTakenException();
            }
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(request.password());
        userDAO.createUser(new UserData(request.username(), hashedPassword, request.email()));
        authDAO.insertAuth(myAuthData);
        return new RegisterResult(myUserData.username(), myAuthData.authToken());
    }

    public void logout(LogoutRequest request) throws DataAccessException, BadRequestException, UnauthorizedException {
        if (authDAO.getAuth(request.authToken()) == null){
            throw new UnauthorizedException();
        }
        authDAO.deleteAuth(request.authToken());
    }

    public LoginResult login(LoginRequest request) throws BadRequestException, DataAccessException, UnauthorizedException, AlreadyTakenException {
        if (request.username() == null || request.password() == null){
            throw new BadRequestException();
        }
        UserData userData = userDAO.getUserData(request.username());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (userData != null){
            if (!encoder.matches(request.password(), userData.password())){
                throw new UnauthorizedException();
            }
        }
        if (userData == null){
            throw new UnauthorizedException();
        }


        AuthData myAuthData = new AuthData(authDAO.generateAuthToken(request.username()), request.username());
        authDAO.insertAuth(myAuthData);
        return new LoginResult(myAuthData.username(), myAuthData.authToken());
    }

}