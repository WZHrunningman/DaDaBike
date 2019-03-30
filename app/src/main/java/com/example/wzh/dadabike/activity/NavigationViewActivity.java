package com.example.wzh.dadabike.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wzh.dadabike.OnItemClickListener;
import com.example.wzh.dadabike.R;

import java.util.ArrayList;

/**
 * Created by ' on 2018/7/29.
 */

public class NavigationViewActivity extends LinearLayout {

    private int image_width;
    private int image_height;
    private float text_size;
    private Context mcontext;

    //选中图片的数组
    private int[] selectedImage;
    //未选中图片的数组
    private int[] unSelectedImage;

    private ArrayList<TextView> textViews = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NavigationViewActivity(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public NavigationViewActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationView);
        image_height = typedArray.getInteger(R.styleable.NavigationView_image_height,15);
        image_width = typedArray.getInteger(R.styleable.NavigationView_image_width,15);
        text_size = typedArray.getDimension(R.styleable.NavigationView_text_size,12);
        typedArray.recycle();
    }
/**
 * 动态添加布局
 *
 * @param titles
 *            导航标题
 * @param selectedImage
 *            选中时的图片
 * @param unSelectedImage
 *            未选中时的图片
 * @param screenWidth
 *            屏幕的宽度
 * @param mHeight
 *            控件自身高度
 * @param context
 */public void setLayout(String[] titles, int[] selectedImage, int[] unSelectedImage, int screenWidth, int mHeight, Context context){
    this.mcontext = context;
    this.selectedImage = selectedImage;
    this.unSelectedImage = unSelectedImage;
    setOrientation(LinearLayout.HORIZONTAL);
    if (titles != null && titles.length != 0){
        int widthScale = screenWidth / titles.length;
        for (int i = 0; i<titles.length;i++){
            LinearLayout linearLayout = new LinearLayout(mcontext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams layoutLp = new LinearLayout.LayoutParams(widthScale, LayoutParams.MATCH_PARENT);
            layoutLp.gravity = Gravity.CENTER;
            linearLayout.setLayoutParams(layoutLp);

            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams imageLp = new LinearLayout.LayoutParams(image_width,image_height);
            imageLp.topMargin = 5;
            imageView.setImageDrawable(context.getResources().getDrawable(unSelectedImage[i]));
            imageView.setLayoutParams(imageLp);

            TextView tv_title = new TextView(context);
            LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            tv_title.setTextSize(text_size);
            tv_title.setText(titles[i]);
            tv_title.setLayoutParams(textLp);

            linearLayout.addView(imageView);
            linearLayout.addView(tv_title);

            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    setColorLing(position);
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            linearLayout.setTag(i);
            addView(linearLayout, widthScale, mHeight);
            imageViews.add(imageView);
            textViews.add(tv_title);

        }
    }
        }

    /**
     * 设置文本和图片为亮色
     *
     * @param position
     */
    public void setColorLing(int position) {
        setColorDark();
        for (int i = 0; i < textViews.size(); i++) {
            if (position == i) {
                textViews.get(i).setTextColor(Color.parseColor("#000000"));
                imageViews.get(i).setImageDrawable(mcontext.getResources().getDrawable(selectedImage[i]));
            }
        }
    }

    /**
     * 设置文本和图片为暗色
     */
    public void setColorDark() {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setTextColor(Color.parseColor("#999999"));
            imageViews.get(i).setImageDrawable(mcontext.getResources().getDrawable(unSelectedImage[i]));
        }
    }


}
