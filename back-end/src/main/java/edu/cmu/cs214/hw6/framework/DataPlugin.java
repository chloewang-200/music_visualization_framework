package edu.cmu.cs214.hw6.framework;

import java.util.List;

public interface DataPlugin {

    /**
     * Get the instruction for users to use the data plugin, will be displayed in front-end
     * @return the instruction
     */
    String getInstruction();


    /**
     * Get the name of the data plugin to be displayed in the front-end.
     * @return the instruction
     */
    String getPluginName();

    /**
     * Get a list of song names extracted by the data plugin.
     * 
     * @param info date or playlist ID or user manual input for each plugin
     * @return the name of all songs to be passed into the framework
     */
    List<String> getSongNames(String info);

    /**
     * Called (only once) when the data plugin is first registered with the
     * framework, giving the data plugin a chance to perform any initial set-up
     * before the game has begun (if necessary).
     * 
     * @param framework The {@link MusicFrameworkImpl} instance with 
     *                  which the data plugin was registered.
     */
    void setApplication(MusicFramework framework);
}
