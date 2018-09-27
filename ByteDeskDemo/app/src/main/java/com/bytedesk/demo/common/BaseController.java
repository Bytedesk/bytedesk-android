package com.bytedesk.demo.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedesk.demo.R;
import com.bytedesk.demo.visitor.tutorial.ChatFragment;
import com.bytedesk.demo.visitor.tutorial.ProfileFragment;
import com.bytedesk.demo.visitor.tutorial.StatusFragment;
import com.bytedesk.demo.visitor.tutorial.ThreadFragment;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cginechen
 * @date 2016-10-20
 */

public abstract class BaseController extends QMUIWindowInsetLayout {

    private HomeControlListener mHomeControlListener;

    public BaseController(Context context) {
        super(context);
    }

    protected void startFragment(BaseFragment fragment) {
        if (mHomeControlListener != null) {
            mHomeControlListener.startFragment(fragment);
        }
    }

    public void setHomeControlListener(HomeControlListener homeControlListener) {
        mHomeControlListener = homeControlListener;
    }

    protected abstract String getTitle();

    public interface HomeControlListener {
        void startFragment(BaseFragment fragment);
    }


}
