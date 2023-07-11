package edu.cmu.cs214.hw6.dataPlugins;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs214.hw6.framework.MusicFramework;

/**
 * Code for class User Manual Input Plugin implements DataPlugin interface.
 */
public class UserManualInputPlugin implements DataPlugin {
    private static final String DATA_PLUGIN_NAME = "User Manual Input";
    private MusicFramework framework;
    private List<String> songs = new ArrayList<>();

    /**
     * Returns the instruction for User Manual Input plugin.
     * The instruction will be displayed on the front-end to help the user know how to use User Manual Input plugin.
     */
    @Override
    public String getInstruction() {
        return "type in song names separated by semicolons, \\n" +
        "example: 'kill bill;flowers;die for you;chemical'";
    }

    /**
     * Returns the data plugin name User Manual Input.
     * This will be displayed in the front-end when the user selects which plugin to use.
     */
    @Override 
    public String getPluginName() {
        return DATA_PLUGIN_NAME;
    }

    /**
     * Returns a list of song names according to user input string.
     * If user input nothing, User Manual Input plugin will return 0 songs.
     * 
     * @param input user manual input, each song is seperated by semicolons
     */
    @Override
    public List<String> getSongNames(String input) {
        this.songs = new ArrayList<>();
        if (input.equals("")) {
            return songs;
        }
        String[] songNames = input.split(";");
        for (int i = 0; i < songNames.length; i++) {
            songs.add(songNames[i]);
        }
        System.out.println(songs);
        System.out.println("total songs: " + songs.size());
        return songs;
    }

    /**
     * Called (only once) when the data plugin is first registered with the
     * framework, giving the data plugin a chance to perform any initial set-up
     * before the framework has begun (if necessary).
     * 
     * @param f The {@link MusicFrameworkImpl} instance with 
     *                  which the data plugin was registered.
     */
    @Override
    public void setApplication(MusicFramework f) {
        this.framework = f;
    }

    
}
