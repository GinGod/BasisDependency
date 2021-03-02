package com.gingold.basisdependency.Base;

import com.gingold.basisdependency.R;
import com.gingold.basislibrary.Base.BasisBaseActivity;
import com.gingold.basislibrary.Base.BasisMultiMethodActivity;
import com.google.gson.Gson;

/**
 *
 */

public abstract class BaseActivity extends BasisMultiMethodActivity {
    public Gson gson = new Gson();

    public void initTitle(String title, String right) {
        super.initTitle(title, right, R.id.iv_base_back, R.id.tv_base_title, R.id.tv_base_right);
    }
}
