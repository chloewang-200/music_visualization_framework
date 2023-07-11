package edu.cmu.cs214.hw6.framework;


/**
 * The Attribute class stores all the types of attributes that 
 * the framework providesfor each song
 */
public final class Attribute {
    private String emotion;

    /**
     * initialize an attribute
     */
    private Attribute(String s) {
        this.emotion = s;
    }

    /**
     * construct an attribute
     */
    protected static Attribute construct(String s) {
        return new Attribute(s);
    }

    /**
     * construct an attribute
     * @return string representation of the attribute for front-end rendering purpose
     */
    public String toString() {
        return this.emotion;
    }

    /**
     * generates an attribute popularity for the visual plugin to call
     * @return the attribute 'popularity'
     */
    public static Attribute popularity() {
        return new Attribute("Popularity");
    }


     /**
     * generates an attribute neutral for the visual plugin to call
     * @return the attribute 'neutral'
     */
    public static Attribute neutral() {
        return new Attribute("Neutral");
    }

    /**
     * generates an attribute positive for the visual plugin to call
     * @return the attribute 'positive'
     */
    public static Attribute positive() {
        return new Attribute("Positive");
    }

     /**
     * generates an attribute negative for the visual plugin to call
     * @return the attribute 'negative'
     */
    public static Attribute negative() {
        return new Attribute("Negative");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute energy() {
        return new Attribute("energy");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute liveness() {
        return new Attribute("liveness");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute dancibility() {
        return new Attribute("dancibility");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute valence() {
        return new Attribute("valence");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute veryNegative() {
        return new Attribute("Very Negative");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute veryPositive() {
        return new Attribute("Very Positive");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute instrumentalness() {
        return new Attribute("instrumentalness");
    }

    /**
     * generates an attribute acousticness for the visual plugin to call
     * @return the attribute 'acousticness'
     */
    public static Attribute speechiness() {
        return new Attribute("speechiness");
    }

    /**
     * generates an attribute year for the visual plugin to call
     * @return the attribute 'year'
     */
    public static Attribute year() {
        return new Attribute("Year");
    }

}
