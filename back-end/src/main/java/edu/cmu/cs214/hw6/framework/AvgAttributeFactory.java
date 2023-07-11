package edu.cmu.cs214.hw6.framework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AvgAttributeFactory {
    
    private HashMap<String, Float> attributesMap;

    /**
     * construct an AvgAttributeFactory
     */
    private AvgAttributeFactory(HashMap<String, Float> map) {
        this.attributesMap = map;
    }

    /**
     * construct an AvgAttributeFactory
     */
    protected static AvgAttributeFactory construct(HashMap<String, Float> map) {
        return new AvgAttributeFactory(map);
    }

    /**
     * get the average attribute for a song
     * @param e the attribute
     * @return a float representation of the average attribute
     */
    public Float getAvgfor(Attribute e) {
        return this.attributesMap.getOrDefault(e.toString(), 0f);
    }

    /**
     * get the set of all attributes provided by the framework
     * @return a set of all attributes
     */
    public Set<Attribute> getEmotions(){
        Set<Attribute> res = new HashSet<Attribute>();
        for (String e: this.attributesMap.keySet()) {
            res.add(Attribute.construct(e));
        }
        return res;
    }
}
