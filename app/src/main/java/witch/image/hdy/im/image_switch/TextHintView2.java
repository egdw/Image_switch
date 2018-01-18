package witch.image.hdy.im.image_switch;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

public class TextHintView2 extends TextView implements HintView2 {
    private int length;
    private ArrayList<Pic> pics;

    public TextHintView2(Context context, ArrayList<Pic> pics) {
        super(context);
        this.pics = pics;
    }

    public TextHintView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initView(int length, int gravity) {
        this.length = length;
        setTextColor(Color.WHITE);
        setTextSize(24f);
        switch (gravity) {
            case 0:
                setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case 1:
                setGravity(Gravity.CENTER);
                break;
            case 2:
                setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
        }

        setCurrent(0);
    }

    @Override
    public void setCurrent(int current) {
//        setText(current + 1 + "/" + length);
        setText(pics.get(current).getName());
    }
}
