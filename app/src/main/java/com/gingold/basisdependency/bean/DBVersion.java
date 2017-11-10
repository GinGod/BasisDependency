package com.gingold.basisdependency.bean;

import com.gingold.basislibrary.db.BasisDBTable;
import com.gingold.basislibrary.db.BasisDBVersion;

import java.util.ArrayList;

/**
 *
 */

public class DBVersion implements BasisDBVersion {
    @Override
    public String getDBName() {
        return "TEST";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public ArrayList<BasisDBTable> getTableList() {
        list.add(new DBTable());
        return list;
    }
}
