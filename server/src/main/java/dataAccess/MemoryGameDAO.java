package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    /**
     * Key is the gameID (an Integer) and the value is the GameData
     * */
    HashMap<Integer, GameData> fakeGameDatabase = new HashMap<>();
    @Override
    public void clear() throws DataAccessException {
        fakeGameDatabase.clear();
    }

    @Override
    public GameData insertGame(GameData gameData) throws DataAccessException {
        if (fakeGameDatabase.get(gameData.gameID()) != null){
            throw new DataAccessException("Game ID already exists");
        }
        fakeGameDatabase.put(gameData.gameID(), gameData);
        return gameData;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException, BadRequestException {
        GameData myGameData = fakeGameDatabase.get(gameID);
        if (myGameData == null){
            throw new BadRequestException("No such gameID exists");
        }
        return myGameData;
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> myGameList = new ArrayList<>();
        for (HashMap.Entry<Integer, GameData> entry : fakeGameDatabase.entrySet()) {
            GameData game = entry.getValue();
            myGameList.add(game);
        }
        return myGameList;
    }

    @Override
    public void updateGame(int gameID, GameData gameData) throws DataAccessException, BadRequestException {
        GameData myGameData = fakeGameDatabase.get(gameID);
        if (myGameData == null){
            throw new BadRequestException("No such gameID exists");
        }
        else {
            fakeGameDatabase.put(gameID, gameData);
        }
    }

    @Override
    public void deleteGame(int gameID) throws DataAccessException {
        if (fakeGameDatabase.remove(gameID) == null){
            throw new DataAccessException("No game was deleted");
        }
    }

    @Override
    public void joinGame(int gameID, ChessGame.TeamColor clientColor, String clientUsername) throws BadRequestException, DataAccessException {
        GameData myGame = fakeGameDatabase.get(gameID);
        GameData myNewGame;
        if (myGame == null){
            throw new BadRequestException("No such gameID exists");
        }
        //since records are immutable, we will overwrite it with new information
        if (clientColor == ChessGame.TeamColor.WHITE){
            if (myGame.whiteUsername() != null){
                throw new DataAccessException("There can only be one player for the white pieces");
            }
            myNewGame = new GameData(gameID, clientUsername, myGame.blackUsername(), myGame.gameName(), myGame.game());
        }
        else {
            if (myGame.blackUsername() != null){
                throw new DataAccessException("There can only be one player for the black pieces");
            }
            myNewGame = new GameData(gameID, myGame.whiteUsername(), clientUsername, myGame.gameName(), myGame.game());
        }
        fakeGameDatabase.put(gameID, myNewGame);
    }

    @Override
    public int generateNewGameID() {
        return fakeGameDatabase.size() + 1;
    }
}