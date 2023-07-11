package edu.cmu.cs214.hw6.mocks;

import edu.cmu.cs214.hw6.framework.VisualPlugin;
import edu.cmu.cs214.hw6.framework.AvgAttributeFactory;
import edu.cmu.cs214.hw6.framework.EachAttributeFactory;
import edu.cmu.cs214.hw6.framework.Attribute;
import edu.cmu.cs214.hw6.framework.MusicFramework;

public class MockVisualPlugin implements VisualPlugin {
    
    public String getPluginName() {
        return "visual stub";
    }

    public String getInstruction() {
        return "this is a visual stub";
    }

    public String draw(Attribute[] emotions, AvgAttributeFactory emotionsMap, 
        EachAttributeFactory eachEmotionsMap) {
        return "";
    }

    public void setApplication(MusicFramework app) {}

}
