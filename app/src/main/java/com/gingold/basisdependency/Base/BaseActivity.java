package com.gingold.basisdependency.Base;

import com.gingold.basisdependency.R;
import com.gingold.basislibrary.Base.BasisBaseActivity;

/**
 *
 */

public abstract class BaseActivity extends BasisBaseActivity {

    public void initTitle(String title, String right) {
        super.initTitle(title, right, R.id.iv_base_back, R.id.tv_base_title, R.id.tv_base_right);
    }
}
