package com.zss.mall.bean;

import java.util.List;

public class Area {

    public long id;
    public int level;
    public String name;
    public long parentId;
    public List<Area> currentLevelList;

}
