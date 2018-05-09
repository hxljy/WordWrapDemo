package test.hhx.com.wordwrapdemo;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author hxl
 */
public class WordWrapView extends ViewGroup {
    private static final int PADDING_HOR = 10;//子view水平方向padding
    private static final int PADDING_VERTICAL = 10;//子view垂直方向padding
    private static final int TEXT_MARGIN_HOR = 20;//子view之间的水平间距
    private static final int TEXT_MARGIN_VERTICAL = 20;//行间距

    private int num = 0;//最多字个数

    /**
     * @param context
     */
    public WordWrapView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * @param context
     * @param attrs
     */
    public WordWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int autualWidth = r - l;//当前容器宽度
        int x = 0;// 横坐标开始
        int y = 0;//纵坐标开始
        int rows = 1;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            x += width + TEXT_MARGIN_HOR;
            if (x > autualWidth - TEXT_MARGIN_HOR) {//判断累积高度
                if (i != 0) {
                    x = width + TEXT_MARGIN_HOR;
                    rows++;
                }

                //当一个子view长度超出父view长度时
                if (x > autualWidth - TEXT_MARGIN_HOR) {//判断单个高度
                    //如果子view是textview的话处理文字
                    if (view instanceof TextView) {
                        TextView tv = ((TextView) view);
                        if (num == 0) {//只计算一次
                            int wordNum = tv.getText().toString().length();
                            num = wordNum * (autualWidth - 2 * TEXT_MARGIN_HOR) / width - 1;
                        }
                        String text = tv.getText().toString();
                        text = text.substring(0, num) + "...";
                        tv.setText(text);
                    }

                    x = autualWidth - TEXT_MARGIN_HOR;
                    width = autualWidth - (2 * TEXT_MARGIN_HOR);
                }
            }
            y = rows * (height + TEXT_MARGIN_VERTICAL);
            view.layout(x - width, y - height, x, y);

        }
    }

    public float getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text))
            return 0;
        float width = 0;
        Paint paint = new Paint();
        paint.setTextSize(size);
        float text_width = paint.measureText(text);// 得到总体长度
        width = text_width / text.length();// 每一个字符的长度

        return width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int x = 0;//横坐标
        int y = 0;//纵坐标
        int rows = 1;//总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualWidth = specWidth;//实际宽度
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
            child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR, PADDING_VERTICAL);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            x += width + TEXT_MARGIN_HOR;
            if (x > actualWidth) {//换行
                if (index != 0) {
                    x = width + TEXT_MARGIN_HOR;
                    rows++;
                }
            }
            y = rows * (height + TEXT_MARGIN_VERTICAL);
        }
        setMeasuredDimension(actualWidth, y + TEXT_MARGIN_VERTICAL);
    }
}
