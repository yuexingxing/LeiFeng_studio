package com.tajiang.leifeng.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")
public class AutoPagerView extends ViewPager implements View.OnClickListener {

    private final int SCROLL_PAGER = 1;

    private Timer timer;

    private List<View> viewList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();

    private ViewPagerAdapter viewPagerAdapter;

    private OnPagerClickListener onPagerClickListener;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_PAGER:

                    // 切换到下一页
                    int scrollPage = getCurrentItem() + 1;
                    setCurrentItem(scrollPage % mImgList.size(), true);

                    break;
            }
        }

        ;
    };

    public AutoPagerView(Context context) {
        this(context, null);
    }

    public AutoPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @SuppressLint("InflateParams")
    public void setImageList(List<String> imgList) {

        mImgList.clear();

        if (imgList.size() > 1) {
            // 预处理一下图片数组
            mImgList.add(imgList.get(imgList.size() - 1));
            mImgList.addAll(imgList);
            mImgList.add(imgList.get(0));
        } else {
            mImgList.addAll(imgList);
        }

        initPagerView(mImgList);

    }

    public void setImageList(String[] imgArr) {

        mImgList.clear();

        if (imgArr.length > 1) {
            mImgList.add(imgArr[imgArr.length - 1]);

            for (int i = 0; i < imgArr.length; i++) {
                mImgList.add(imgArr[i]);
            }

            mImgList.add(imgArr[0]);
        } else {
            mImgList.add(imgArr[0]);
        }


        initPagerView(mImgList);

    }

    @SuppressLint("InflateParams")
    private void initPagerView(List<String> mImgList) {

        for (int i = 0; i < mImgList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_image, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_testPic);
            imageView.setOnClickListener(this);
            ImageUtils.loadImage(getContext(), imageView, mImgList.get(i));
            viewList.add(view);
        }

        viewPagerAdapter = new ViewPagerAdapter(viewList);
        setAdapter(viewPagerAdapter);

        setOnPageChangeListener(onPageChangeListener);

        // 设置默认显示第二张
        setCurrentItem(1);

        if (timer == null) {

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(SCROLL_PAGER);
                }
            };

            timer = new Timer();
            timer.schedule(task, 5000, 5000);

        }

    }

    @Override
    public void onClick(View v) {
        if (onPagerClickListener != null) {
            onPagerClickListener.onPagerClick(getCurrentItem() - 1);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private List<View> mListViews;

        public ViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                if (getCurrentItem() == 0) {
                    setCurrentItem(mImgList.size() - 2, false);
                } else if (getCurrentItem() == mImgList.size() - 1) {
                    setCurrentItem(1, false);
                }
            }
        }
    };

    public interface OnPagerClickListener {
        public void onPagerClick(int position);
    }

    public void setOnPagerClickListener(OnPagerClickListener onPagerClickListener) {
        this.onPagerClickListener = onPagerClickListener;
    }

}
