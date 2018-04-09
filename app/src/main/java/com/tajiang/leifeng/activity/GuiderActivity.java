package com.tajiang.leifeng.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.ViewPagerAdapter;
import com.tajiang.leifeng.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GuiderActivity extends BaseActivity implements OnClickListener {

    private static final int USER_GUIDES = 3;
    private int currentUserGuide = 0;

    // 引导页图片Id
    private final int[] imgGuideIdArr = {
            R.drawable.img_guide_01,
            R.drawable.img_guide_02,
            R.drawable.img_guide_03,
//            R.drawable.img_guide_04,
    };

    // 新手引导页图片Id
    private final int[] userGuideIdArr = {
            R.drawable.user_guide_01,
            R.drawable.user_guide_02,
            R.drawable.user_guide_03,
            R.drawable.user_guide_04,
            R.drawable.user_guide_05,
    };

    private TextView tv_comeInGuide;

    private ViewPager vp_guide;

    private View viewUserGuide;

    private ViewPagerAdapter viewPagerAdapter;

    private List<View> viewList = new ArrayList<>();


    @Override
    protected void initTopBar() {
        unableNav();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_guide);
        resetWindow();
    }

    private void resetWindow() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void initView() {
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        tv_comeInGuide = (TextView) findViewById(R.id.tv_comeInGuide);
        viewUserGuide = findViewById(R.id.view_user_guide);
    }

    @Override
    protected void initAdapter() {
        viewPagerAdapter = new ViewPagerAdapter(viewList);
    }

    @Override
    protected void initDates() {

        // 添加引导页面
        for (int i = 0; i < imgGuideIdArr.length; i++) {
            View page = getLayoutInflater().inflate(R.layout.layout_guide, null);
            ImageView iv_guide = (ImageView) page.findViewById(R.id.iv_guide);
            iv_guide.setImageResource(imgGuideIdArr[i]);
            viewList.add(page);
        }

        vp_guide.setAdapter(viewPagerAdapter);

    }

    @Override
    protected void initListener() {
        tv_comeInGuide.setOnClickListener(this);
        viewUserGuide.setOnClickListener(this);
        vp_guide.setOnPageChangeListener(pageChangeListener);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_comeInGuide:
//                intent2Activity(SchoolSelectAreaActivity.class);
//                finish();
                viewUserGuide.setVisibility(View.VISIBLE);
                break;
            case R.id.view_user_guide:
                if (currentUserGuide == USER_GUIDES) {
                    intent2Activity(SchoolSelectAreaActivity.class);
                    finish();
                } else {
                    viewUserGuide.setBackgroundResource(userGuideIdArr[++currentUserGuide]);
                }
                break;
        }
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageSelected(int position) {
            // 滑到最后一页的时候显示进入应用按钮
            tv_comeInGuide.setVisibility(position == imgGuideIdArr.length - 1 ? View.VISIBLE : View.INVISIBLE);
        }

    };

    @Override
    protected void initStateBar() {
    }
}
