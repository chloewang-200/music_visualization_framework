package edu.cmu.cs214.hw6.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cmu.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs214.hw6.framework.MusicFramework;

public class MockDataPluginOneSong implements DataPlugin {
    
    public String getInstruction() {
        return "this is a stub plugin";
    }

    public String getPluginName() {
        return "data stub";
    }

    public List<String> getSongNames(String info) {
        return new ArrayList<String>(Arrays.asList("hello"));
    }
  
   public void setApplication(MusicFramework framework) {}
}
