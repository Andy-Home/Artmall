package com.count.andy.structure;

import java.util.ArrayList;

/**
 * Created by andy on 15-12-3.
 */
public class FourList {
    public ArrayList<FourGrid> fourGrids = new ArrayList<FourGrid>();
    public String name;

    public FourList(String name, ArrayList<FourGrid> fourGrids) {
        this.fourGrids = fourGrids;
        this.name = name;
    }
}
