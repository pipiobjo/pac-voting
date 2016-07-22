package com.prodyna.pac.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjoern on 06.06.16.
 */
public class Options {
   Map<String, String> options = new HashMap<>();
    public Options(){

    }

    public void setOptions(Map<String, String> options){
        this.options = options;
    }

    public Map<String, String> getOptions(){
        return this.options;
    }
}
