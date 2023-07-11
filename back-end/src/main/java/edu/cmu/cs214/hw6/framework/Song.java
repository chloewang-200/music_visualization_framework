package edu.cmu.cs214.hw6.framework;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * The Song class represents a song in the framework.
 */
public class Song {
    String id;
    String songName;
    float energy;
    float instrumentalness;
    float liveness;
    float speechiness;
    float dancibility;
    float tempo;
    float valence;
    int popularity;
    int year;
    List<String> artists;
    String [] genres;
    HashMap<String, Integer> emotionsMap;

    /**
     * Creates a new song object.
     * @param songName the name of the song
     */
    protected Song(String songName) {
        this.songName = songName;
        this.artists = new ArrayList<String>();
        this.emotionsMap = new HashMap<>();
        this.year = 0;
        String[] emotions = {"Neutral","Very Negative", "Very Positive", 
        "Negative","Positive"};
        for (String emo: emotions) {
            emotionsMap.put(emo, 0);
        }
    }
    
     /**
     * gets the neutral emotion value
     * @return the neutral emotion value
     */
    protected int getNeutral() {
        return this.emotionsMap.get("Neutral");
    }
    
    /**
     * gets the year of the song
     * @return the year of the song
     */
    protected int getYear() {
       return this.year;
    }
    
    /**
     * gets the very negative emotion value
     * @return the very negative emotion value
     */
    protected int getVeryNegative() {
        return this.emotionsMap.get("Very Negative");
    }

    /**
     * gets the popularity of the song
     * @return the popularity of the song
     */
    protected int getPopularity() {
        return this.popularity;
    }

    /**
     * gets the very positive emotion value
     * @return the very positive emotion value
     */
    protected int getVeryPositive() {
        return this.emotionsMap.get("Very Positive");
    }

    /**
     * gets the negative emotion value
     * @return the negative emotion value
     */
    protected int getNegative() {
        return this.emotionsMap.get("Negative");
    }

    /**
     * gets the positive emotion value
     * @return the positive emotion value
     */
    protected int getPositive() {
        return this.emotionsMap.get("Positive");
    }

    /**
     * gets the name of the song
     * @return the name of the song
     */
    protected String getSongName() {
        return this.songName;
    }

    /**
     * gets the artists of the song
     * @return the artists of the song
     */
    protected List<String> getArtists() {
        // clone to make the field immutable
        return new ArrayList<String>(this.artists);
    }

    /**
     * gets the genres of the song
     * @return the genres of the song
     */
    protected String[] getGenres() {
        // clone to make the field immutable
        return this.genres.clone();
    }

    /**
     * gets the spotify id of the song
     * @return the spotify id of the song
     */
    protected float getEnergy(){
        return this.energy;
    }
    
    /**
     * gets the energy of the song
     * @return the energy of the song
     */
    protected float getInstrumentalness() {
        return this.instrumentalness;
    }

    /**
     * gets the liveness of the song
     * @return the liveness of the song
     */
    protected float getLiveness() {
        return this.liveness;
    }

    /**
     * gets the speechiness of the song
     * @return the speechiness of the song
     */
    protected float getSpeechiness() {
        return this.speechiness;
    }

    /**
     * gets the dancibility of the song
     * @return the dancibility of the song
     */
    protected float getDancibility() {
        return this.dancibility;
    }

    /**
     * gets the tempo of the song
     * @return the tempo of the song
     */
    protected float getTempo() {
        return this.tempo;
    }

    /**
     * gets the valence of the song
     * @return the valence of the song
     */
    protected float getValence() {
        return this.valence;
    }

    /**
     * gets the spotify id of the song using the spotify api
     * @return the spotify id of the song
     */
    protected boolean getSpotifyId() {
        SearchTracksRequest searchTracksRequest = SpotifyApiGenerator.getSpotifyApi()
        .searchTracks(this.songName)
        .limit(1)
        .build();
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();
            // default: get the first searched song
            this.id = trackPaging.getItems()[0].getId();
            for (ArtistSimplified artist: trackPaging.getItems()[0].getArtists()) {
                this.artists.add(artist.getName());
            }
            this.popularity = trackPaging.getItems()[0].getPopularity();
            String yearString = trackPaging.getItems()[0].getAlbum().getReleaseDate();
            int tmpYear = Integer.parseInt(
                yearString.substring(0, Math.min(yearString.length(), 4)));
            if (Year.now().getValue() >= tmpYear && tmpYear >= 1900) {
                this.year = tmpYear;
            } else {
                this.year = -1;
            }
            System.out.println(this.year);
            if (this.songName.replaceAll(" ", "")
                .equalsIgnoreCase(trackPaging.getItems()[0].getName().replaceAll(" ", ""))) {
                return true;
            }
            return false;
          } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
          }
    }

    /**
     * gets the lyrics of the song by fetching from musixmatch api
     * @return the lyrics of the song
     */
    private String getLyrics() {
        try {
            String lyric = MusixApi.getLyrics(this.songName, this.artists.get(0));
            return lyric;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * gets the sentiment of the lyrics of the song by using the SentimentAnalyzer class
     * @return the sentiment of the lyrics of the song
     */
    protected boolean getLyricSentiment() {
            String lyric = this.getLyrics();
            String[] lyrics = lyric.split("\\r?\\n|\\r");
            for(int i=0; i < lyrics.length; i++){
                String sentiment = SentimentAnalyzer.findSentiment(lyrics[i]);
                if (emotionsMap.containsKey(sentiment)) {
                    emotionsMap.put(sentiment, (emotionsMap.get(sentiment) + 1));
                }
            }  
            return true;
        
    }

     /**
     * fetch attribbutes of the song using the spotify api
     * @return true if the attributes are fetched successfully
     */
    protected boolean fetchAttributes() {
        try {
            GetAudioFeaturesForTrackRequest audioFeatureRequest = SpotifyApiGenerator
                .getSpotifyApi()
                .getAudioFeaturesForTrack(this.id)
                .build();
            final AudioFeatures audioFeatures = audioFeatureRequest.execute();
            this.dancibility = audioFeatures.getDanceability();
            this.energy = audioFeatures.getEnergy();
            this.valence = audioFeatures.getValence();
            this.liveness = audioFeatures.getLiveness();
            this.instrumentalness = audioFeatures.getInstrumentalness();
            this.speechiness = audioFeatures.getSpeechiness();
            this.tempo = audioFeatures.getTempo();
            System.out.println("energy" + this.energy);
            return true;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
      }
}
