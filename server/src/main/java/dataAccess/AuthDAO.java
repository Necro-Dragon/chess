package dataAccess;

import model.AuthData;

public interface AuthDAO {

    /**Clears all Auth data.
     * @throws DataAccessException if an error occurs during data access
     */
    void clear() throws DataAccessException;

    /**Creates a new Auth record.
     * @param authData the Auth data to create
     * @return the created Auth data
     * @throws DataAccessException if an error occurs during data access
     */
    AuthData insertAuth(AuthData authData) throws DataAccessException;

    /**Gets Auth data based on provided Auth information.
     * @param authData the Auth data to get
     * @return the retrieved Auth data
     * @throws DataAccessException if an error occurs during data access
     * @throws DataNotFoundException if the Auth token is not found
     */
    AuthData getAuth(AuthData authData) throws DataAccessException, DataNotFoundException;

    /**Deletes Auth data.
     * @param authData the Auth data to delete
     * @throws DataAccessException if an error occurs during data access
     */
    void deleteAuth(String authToken) throws DataAccessException, DataNotFoundException;

    /**Confirms Auth data.
     * @param authData the Auth data to confirm
     * @throws DataAccessException if an error occurs during data access
     */
    void confirmAuth(String authToken) throws DataAccessException, DataAccessUnauthorizedException;
    String getUsername(String authToken) throws DataAccessException, DataNotFoundException, DataAccessUnauthorizedException;
    public String generateAuthToken(String username);

}
