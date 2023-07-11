package edu.cmu.cs214.hw6.framework;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

public class SpotifyApiGenerator {
    public String accessToken;
    public String expiresIn;
    // change this to your own spotify api
    // you can generate one at https://developer.spotify.com/documentation/web-api
    private static final String CLIENT_ID = "Replace With Your Client ID";
    private static final String CLIENT_SECRET = "Replace With Your Client Secret ";
    private static final SpotifyApi SPOTIFY_API = new SpotifyApi.Builder()
        .setClientId(CLIENT_ID)
        .setClientSecret(CLIENT_SECRET)
        .build();
    private static ClientCredentialsRequest clientCredentialsRequest;
    private static ClientCredentials clientCredentials;
    
    /**
     * initialize a spotifyApi token
     * @return a spotifyApi
     */
    public static SpotifyApi initSpotifyApi() {
        try {
            clientCredentialsRequest = SPOTIFY_API.clientCredentials()
                .build();
            clientCredentials = clientCredentialsRequest.execute();
    
            // Set access token for further "spotifyApi" object usage
            SPOTIFY_API.setAccessToken(clientCredentials.getAccessToken());
    
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            System.out.println("new access token is" + clientCredentials.getAccessToken());
            return SPOTIFY_API;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return SPOTIFY_API;
        }
    }

    /**
     * get a spotifyApi token, refresh to get a new one if existing one
     * expires in 20 seconds
     * @return a spotifyApi
     */
    public static SpotifyApi getSpotifyApi() {
        if (clientCredentials == null) {
            System.out.println("null");
        }
        if (clientCredentials != null && clientCredentials.getExpiresIn() > 2000) {
            return SPOTIFY_API;
        }
        else {
            return initSpotifyApi();
        }
    }
}
