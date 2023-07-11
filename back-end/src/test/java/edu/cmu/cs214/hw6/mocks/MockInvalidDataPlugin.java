package edu.cmu.cs214.hw6.mocks;

import java.util.List;

import edu.cmu.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs214.hw6.framework.MusicFramework;

public class MockInvalidDataPlugin implements DataPlugin {
   
    public String getInstruction() {
        return null;
    }

    public String getPluginName() {
        return null;
   }

    public List<String> getSongNames(String info) {
        return null;
    }

   public void setApplication(MusicFramework framework) {}
}
