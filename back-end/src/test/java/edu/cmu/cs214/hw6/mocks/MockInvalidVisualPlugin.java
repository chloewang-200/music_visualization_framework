package edu.cmu.cs214.hw6.mocks;

import edu.cmu.cs214.hw6.framework.AvgAttributeFactory;
import edu.cmu.cs214.hw6.framework.EachAttributeFactory;
import edu.cmu.cs214.hw6.framework.Attribute;
import edu.cmu.cs214.hw6.framework.MusicFramework;
import edu.cmu.cs214.hw6.framework.VisualPlugin;

public class MockInvalidVisualPlugin implements VisualPlugin {
    
    public String getPluginName() {
        return "visual stub";
    }

    public String getInstruction() {
        return "this is a visual stub";
    }

    public String draw(Attribute[] emotions, AvgAttributeFactory emotionsMap, 
    EachAttributeFactory eachEmotionsMap) {
        return null;
    }

    public void setApplication(MusicFramework app) {}
}
