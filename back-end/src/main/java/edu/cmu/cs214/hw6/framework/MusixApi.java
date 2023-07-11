package edu.cmu.cs214.hw6.framework;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.jmusixmatch.entity.lyrics.Lyrics;

/**
* The MusixApi class provides a wrapper for the MusixMatch API that fetches lyrics
*/
public class MusixApi {
    
    // change this to your own MusixMatch Api token, you can generate one at
    // https://developer.musixmatch.com/
    // Replace with your own MusixMatch Api token
    private static String apiToken = "Replace With Your Token";
    private static MusixMatch musixMatch = new MusixMatch(apiToken);

    /**
     * Get the lyrics of a song
     * @param song the name of the song
     * @param artist the name of the artist
     * @return the lyrics of the song
     * @throws Exception
     */
    public static String getLyrics(String song, String artist) throws Exception {
        // Track Search [ Fuzzy ]
        Track track = musixMatch.getMatchingTrack(song, artist);
        TrackData data = track.getTrack();
        int trackID = data.getTrackId();
        Lyrics lyrics = musixMatch.getLyrics(trackID);
        return lyrics.getLyricsBody();
    }
}
