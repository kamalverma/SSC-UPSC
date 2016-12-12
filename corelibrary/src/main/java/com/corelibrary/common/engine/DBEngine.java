package com.corelibrary.common.engine;

/**
 * Created by Kamal on 12/12/16.
 */

public class DBEngine {

    private static DBEngine mDbEngine;

    private DBEngine() {

    }

    public static DBEngine getInstance() {
        if (mDbEngine == null) {
            mDbEngine = new DBEngine();
        }

        return mDbEngine;
    }
}
