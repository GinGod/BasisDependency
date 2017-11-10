package com.gingold.basisdependency.bean;

import com.gingold.basislibrary.db.BasisDBTable;

/**
 *
 */

public class DBTable implements BasisDBTable {
    public String name;
    public String age;
    public String sex;

    @Override
    public String getTableName() {
        return "table1";
    }
}
