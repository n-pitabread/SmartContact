package com.pieronex.smartcontact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by PieroNEX on 11/21/14 AD.
 */
public class CircleImageView extends ImageButton{

        public CircleImageView(Context context) {
            super(context);
        }

        public CircleImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float radius = 360.0f; // angle of round corners
            Path clipPath = new Path();
            RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
            clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
            canvas.clipPath(clipPath);

            super.onDraw(canvas);
        }

}
