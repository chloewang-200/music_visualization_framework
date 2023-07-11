package edu.cmu.cs214.hw6.framework;

public interface VisualPlugin {

    /**
     * get the name of the plugin for front-end redering purpose.
     * @return the name of the plugin
     */
    String getPluginName();

    /**
     * get the string representation of the visualized data with the given emotions
     * @param attribute the attribute provided by the framework
     * @param avgMap the map of the average value of each attribute
     * @param eachMap the map of the value of each attribute for each song
     * @return the string representation of the visualized data
     */
    String draw(Attribute[] attribute, AvgAttributeFactory avgMap, 
    EachAttributeFactory eachMap);
}