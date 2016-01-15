package common.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.usinformatics.nytrip.R;


public class FontButton extends Button {

    private Typeface mFontTypeface;


    public FontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initNewFontIfPresentIn(attrs);
    }


    /**
     * http://www.techrepublic.com/article/pro-tip-extend-androids-textview-to-use-custom-fonts/
     * @param context
     * @param attrs
     */
    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNewFontIfPresentIn(attrs);

    }


    /**
     * http://www.techrepublic.com/article/pro-tip-extend-androids-textview-to-use-custom-fonts/
     * @param context
     */
    public FontButton(Context context) {
        super(context);
        initNewFontIfPresentIn(null);
    }

    private void initNewFontIfPresentIn(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
            String fontName = a.getString(R.styleable.FontTextView_fontName);
            if (fontName!=null) {
                mFontTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
            }
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mFontTypeface !=null)
            setTypeface(mFontTypeface);
    }

}
