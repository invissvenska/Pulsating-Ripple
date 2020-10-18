package nl.invissvenska.pulsatingripple;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class PulsatingLayout extends RelativeLayout {

    private static final int DEFAULT_RIPPLE_COUNT = 6;
    private static final int DEFAULT_DURATION_TIME = 3000;
    private static final float DEFAULT_SCALE = 6.0f;
    private static final int DEFAULT_FILL_TYPE = 0;

    private float rippleStrokeWidth;
    private Paint paint;
    private boolean animationRunning = false;
    private AnimatorSet animatorSet;
    private ArrayList<RippleView> rippleViewList = new ArrayList<>();

    public PulsatingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PulsatingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public PulsatingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initializes the ripple views
     *
     * @param context
     * @param attrs
     */
    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;

        if (null == attrs) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }

        // Reading the attributes
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PulsatingLayout);
        int color = typedArray.getColor(R.styleable.PulsatingLayout_pr_color, getResources().getColor(R.color.rippleColor));
        rippleStrokeWidth = typedArray.getDimension(R.styleable.PulsatingLayout_pr_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        float radius = typedArray.getDimension(R.styleable.PulsatingLayout_pr_radius, getResources().getDimension(R.dimen.rippleRadius));
        int durationTime = typedArray.getInt(R.styleable.PulsatingLayout_pr_duration, DEFAULT_DURATION_TIME);
        int amount = typedArray.getInt(R.styleable.PulsatingLayout_pr_rippleAmount, DEFAULT_RIPPLE_COUNT);
        float scale = typedArray.getFloat(R.styleable.PulsatingLayout_pr_scale, DEFAULT_SCALE);
        int type = typedArray.getInt(R.styleable.PulsatingLayout_pr_type, DEFAULT_FILL_TYPE);
        typedArray.recycle();

        int delay = durationTime / amount;

        // initialize ripples
        initializePaint(color, type);
        LayoutParams rippleParams = initializeView(radius);
        initializeAnimators(rippleParams, amount, scale, delay, durationTime);
    }

    private void initializePaint(int color, int rippleType) {
        paint = new Paint();
        paint.setAntiAlias(true);
        if (rippleType == DEFAULT_FILL_TYPE) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(rippleStrokeWidth);
        }
        paint.setColor(color);
    }

    private LayoutParams initializeView(float radius) {
        LayoutParams rippleParams = new LayoutParams((int) (2 * (radius + rippleStrokeWidth)), (int) (2 * (radius + rippleStrokeWidth)));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);
        return rippleParams;
    }

    private void initializeAnimators(final LayoutParams params, final int amount, final float scale, final int delay, final int durationTime) {
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            RippleView rippleView = new RippleView(getContext());
            addView(rippleView, params);
            rippleViewList.add(rippleView);
            animatorList.add(animateScaleX(rippleView, scale, delay, durationTime, i));
            animatorList.add(animateScaleY(rippleView, scale, delay, durationTime, i));
            animatorList.add(animateAlpha(rippleView, delay, durationTime, i));
        }
        animatorSet.playTogether(animatorList);
    }

    private ObjectAnimator animateScaleX(final RippleView view, final float scale, final int delay, final int durationTime, final int index) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "ScaleX", 1.0f, scale);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setStartDelay(index * delay);
        animator.setDuration(durationTime);
        return animator;
    }

    private ObjectAnimator animateScaleY(final RippleView view, final float scale, final int delay, final int durationTime, final int index) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "ScaleY", 1.0f, scale);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setStartDelay(index * delay);
        animator.setDuration(durationTime);
        return animator;
    }

    private ObjectAnimator animateAlpha(final RippleView view, final int delay, final int durationTime, final int index) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "Alpha", 1.0f, 0f);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setStartDelay(index * delay);
        animator.setDuration(durationTime);
        return animator;
    }

    private class RippleView extends View {

        public RippleView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius = (Math.min(getWidth(), getHeight())) / 2;
            canvas.drawCircle(radius, radius, radius - rippleStrokeWidth, paint);
        }
    }

    public void startAnimation() {
        if (!isRippleAnimationRunning()) {
            for (RippleView rippleView : rippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }

    public void stopAnimation() {
        if (isRippleAnimationRunning()) {
            for (RippleView rippleView : rippleViewList) {
                rippleView.setVisibility(INVISIBLE);
            }
            animatorSet.end();
            animationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return animationRunning;
    }
}
