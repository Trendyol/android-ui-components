package com.trendyol.uicomponents.imageslider;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.trendyol.uicomponents.imageslider.touchdelegator.ZoomWindowTouchListener;

import static android.view.MotionEvent.ACTION_POINTER_INDEX_MASK;
import static android.view.MotionEvent.ACTION_POINTER_INDEX_SHIFT;
import static android.view.MotionEvent.ACTION_POINTER_UP;

public class CVFloatingZoomView extends FrameLayout implements View.OnTouchListener {

    public static final double ZOOM_THRESHOLD = 1.02;
    public static final double FADEOUT_FACTOR = 1 / 2;
    public static final int MAX_ALPHA_VALUE = 255;

    private final ColorDrawable blackBackground;
    private ImageView originalImage;


    private boolean isBusy;
    private boolean isAnimPlaying;

    private ViewGroup rootLayout;
    private ImageView floatingImage;

    private Point initialPoint;
    private Point initialZoomPoint;
    private Point lastCenter;
    private Point currentCenter;
    private Integer remainingFingerIndex;
    private float initialDifference;
    private float currentDistance;
    private float leftoverZoom;
    private ZoomWindowTouchListener zoomWindowTouchListener;

    public ImageView getOriginalImage() {
        return originalImage;
    }


    private enum FINGER_SWAP_STATE {
        ONE_FINGER,
        TWO_FINGER
    }

    private FINGER_SWAP_STATE CURRENT_FINGER_SWAP_STATE;

    public CVFloatingZoomView(Context context, ImageView imageView, ZoomWindowTouchListener zoomWindowTouchListener) {
        super(context);
        this.zoomWindowTouchListener = zoomWindowTouchListener;
        this.originalImage = imageView;
        this.rootLayout = this;
        blackBackground = new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.black));
        rootLayout.setBackground(blackBackground);
        blackBackground.setAlpha(0);
        this.originalImage.setOnTouchListener(this);
    }

    public boolean isInteractionStartEvent(MotionEvent event, int motionEvent) {
        return motionEvent == MotionEvent.ACTION_POINTER_DOWN && event.getPointerCount() == 2;
    }

    boolean semiBusy = false;

    public boolean onTouch(View v, MotionEvent event) {
        int motionEvent = event.getAction() & MotionEvent.ACTION_MASK;
        int index = (event.getAction() & ACTION_POINTER_INDEX_MASK) >> ACTION_POINTER_INDEX_SHIFT;
        currentCenter = Util.calculateAverageTouch(event);
        if (!isBusy && event.getAction() == MotionEvent.ACTION_DOWN) {
            semiBusy = true;
        }
        if (semiBusy && (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP)) {
            semiBusy = false;
        }

        if (!isBusy && isInteractionStartEvent(event, motionEvent)) {
            initialDifference = Util.calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
            initialPoint = new Point(currentCenter.x, currentCenter.y);
            initialZoomPoint = new Point(initialPoint.x, initialPoint.y);
            leftoverZoom = 0;
        } else if (isBusy && isInteractionStartEvent(event, motionEvent)) {
            onFingerCountChange(event, FINGER_SWAP_STATE.TWO_FINGER);
            leftoverZoom = floatingImage.getScaleX() - 1;
            initialDifference = Util.calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        } else if (!isBusy && motionEvent == MotionEvent.ACTION_MOVE && event.getPointerCount() > 1) {
            currentDistance = Util.calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
            float zoomRatio = (leftoverZoom + (currentDistance / initialDifference));
            if (zoomRatio > ZOOM_THRESHOLD) {
                onInteractionStart();
            }
        } else if (isBusy && motionEvent == MotionEvent.ACTION_MOVE) {
            if (event.getPointerCount() > 1) {
                currentDistance = Util.calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                float zoomRatio = (leftoverZoom + (currentDistance / initialDifference));

                if (initialZoomPoint == null) {
                    initialZoomPoint = currentCenter;
                }

                if (shouldMove()) {
                    onFloatingImageMove(currentCenter);
                }
                onFloatingImageZoom(zoomRatio, initialZoomPoint);


            } else {
                if (CURRENT_FINGER_SWAP_STATE == FINGER_SWAP_STATE.TWO_FINGER) {
                    onFingerCountChange(event, FINGER_SWAP_STATE.ONE_FINGER);
                } else if (CURRENT_FINGER_SWAP_STATE == FINGER_SWAP_STATE.ONE_FINGER) {
                    onFloatingImageMove(currentCenter);
                }
            }

        }
        //EXIT STATES
        if (isBusy) {
            if (motionEvent == MotionEvent.ACTION_UP && event.getPointerCount() == 1) {
                playEndAnimation();
            } else if (motionEvent == ACTION_POINTER_UP && remainingFingerIndex != null && remainingFingerIndex == index) {
                playEndAnimation();
            } else if (motionEvent == MotionEvent.ACTION_CANCEL) {
                playEndAnimation();
            }
        }
        if (!semiBusy && !isBusy && ((event.getAction() == MotionEvent.ACTION_UP))) {
            ((View) originalImage.getParent()).performClick();
        }
        lastCenter = currentCenter;
        return semiBusy || isBusy;
    }

    private boolean shouldMove() {
        return lastCenter != null && !isAnimPlaying;
    }

    public void onInteractionStart() {
        if (isBusy && !(getContext() instanceof Activity)) {
            return;
        }
        originalImage.setVisibility(INVISIBLE);
        initViews();
        initPositions();
        ((Activity) getContext()).addContentView(rootLayout, rootLayout.getLayoutParams());
        CURRENT_FINGER_SWAP_STATE = FINGER_SWAP_STATE.TWO_FINGER;
        isBusy = true;
        if (zoomWindowTouchListener != null) {
            zoomWindowTouchListener.onHandlingStart(this);
        }
    }

    @SuppressLint("RtlHardcoded")
    private void initViews() {
        floatingImage = new ImageView(getContext());
        rootLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatingImage.setLayoutParams(new LayoutParams(originalImage.getWidth(), originalImage.getHeight(), Gravity.LEFT | Gravity.TOP));
        Point point = Util.getViewPosition(originalImage);
        ((MarginLayoutParams) floatingImage.getLayoutParams()).leftMargin = point.x;
        ((MarginLayoutParams) floatingImage.getLayoutParams()).topMargin = point.y;
        floatingImage.setImageDrawable(originalImage.getDrawable());
        floatingImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void initPositions() {

        floatingImage.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rootLayout.measure(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        rootLayout.addView(floatingImage, floatingImage.getLayoutParams());
        Util.setPivot(floatingImage, new Point(Util.getScaledWidth(floatingImage) / 2, Util.getScaledHeight(floatingImage) / 2));
        floatingImage.setVisibility(View.VISIBLE);
        floatingImage.setScaleY(1);
        floatingImage.setScaleX(1);
    }


    public void onFloatingImageMove(Point point) {
        if (initialPoint == null) {
            initialPoint = point;
        }
        floatingImage.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        Point differenceVector = Util.getDifferenceVector(initialPoint, point);
        ((MarginLayoutParams) floatingImage.getLayoutParams()).leftMargin = (int) (originalImage.getX() + differenceVector.x);
        ((MarginLayoutParams) floatingImage.getLayoutParams()).topMargin = (int) (originalImage.getY() + differenceVector.y);
        floatingImage.requestLayout();
    }

    public void onFloatingImageZoom(float scaleFactor, Point focusPoint) {
        if (floatingImage.getScaleX() * scaleFactor > 1) {
            Util.setPivot(floatingImage, focusPoint);
            Util.setScale(floatingImage, scaleFactor);
            float totalScaleFactor = scaleFactor * floatingImage.getScaleX(); // ie. 1.2
            float scaleRatio = totalScaleFactor - 1; //ie. 0.2 = % 20
            float zoomRatio = (float) Math.sqrt(scaleRatio);
            int backgroundAlpha = (int) Math.max(0, Math.min(MAX_ALPHA_VALUE * FADEOUT_FACTOR, zoomRatio * MAX_ALPHA_VALUE * FADEOUT_FACTOR));
            blackBackground.setAlpha(backgroundAlpha);
        }
    }

    public void playEndAnimation() {
        if (!isAnimPlaying) {
            ViewPropertyAnimator endAnimation = Util.createFinishAnimation(floatingImage, originalImage);
            ObjectAnimator fadeAnimation = Util.createFadeOut(blackBackground);
            endAnimation.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimPlaying = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimPlaying = false;
                    onInteractionEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimPlaying = false;
                    onInteractionEnd();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            fadeAnimation.setDuration(endAnimation.getDuration()).start();
            endAnimation.start();
        }
    }

    public void onFingerCountChange(MotionEvent event, FINGER_SWAP_STATE newstate) {
        initialPoint.set(initialPoint.x - (lastCenter.x - currentCenter.x), initialPoint.y - (lastCenter.y - currentCenter.y));
        lastCenter = currentCenter;
        remainingFingerIndex = 1 - ((event.getAction() & ACTION_POINTER_INDEX_MASK) >> ACTION_POINTER_INDEX_SHIFT);
        CURRENT_FINGER_SWAP_STATE = newstate;
    }

    public void onInteractionEnd() {
        if (isBusy && rootLayout != null && rootLayout.getParent() != null) {
            isBusy = false;
            if (zoomWindowTouchListener != null) {
                zoomWindowTouchListener.onHandlingEnd(this);
            }
            originalImage.setVisibility(VISIBLE);
            ((ViewGroup) rootLayout.getParent()).removeView(rootLayout);
            initialPoint = null;
            initialZoomPoint = null;
            lastCenter = null;
            currentCenter = null;
            remainingFingerIndex = null;
            initialDifference = 0;
            floatingImage.setPivotX(Util.getScaledWidth(floatingImage) / 2);
            floatingImage.setPivotY(Util.getScaledHeight(floatingImage) / 2);
            rootLayout.removeView(floatingImage);
            CURRENT_FINGER_SWAP_STATE = null;
            originalImage.invalidate();
            originalImage.requestLayout();
        }
    }

    public interface FloatingZoomListener {

        void onHandlingStart(CVFloatingZoomView instance);

        void onHandlingEnd(CVFloatingZoomView instance);
    }

    private static class Util {

        static final int RETURN_ANIM_DURATION = 350;

        private static Point getDifferenceVector(Point a, Point b) {
            return new Point(b.x - a.x, b.y - a.y);
        }

        private static Point calculateAverageTouch(MotionEvent event) {
            int totalX = 0;
            int totalY = 0;

            for (int i = 0; i < event.getPointerCount(); i++) {
                totalX += event.getX(i);
                totalY += event.getY(i);
            }

            return new Point(totalX / event.getPointerCount(), totalY / event.getPointerCount());
        }

        public static float calculateDistance(Point a, Point b) {
            return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        }

        private static float calculateDistance(float x, float y, float x1, float y1) {
            return (float) Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
        }

        private static int getScaledHeight(View view) {
            return (int) (view.getScaleY() * view.getHeight());
        }

        private static int getScaledWidth(View view) {
            return (int) (view.getScaleX() * view.getWidth());
        }

        public float getMagnitude(Point point) {
            return calculateDistance(point.x, point.y, 0, 0);
        }

        private static void setPivot(View view, Point point) {
            view.setPivotY(point.y);
            view.setPivotX(point.x);
        }

        private static void setScale(View view, float scaleFactor) {
            view.setScaleY(scaleFactor);
            view.setScaleX(scaleFactor);
        }

        private static Point getViewPosition(View view) {
            int[] location = {0, 0};
            view.getLocationInWindow(location);
            return new Point(location[0], location[1]);
        }

        private static ViewPropertyAnimator createFinishAnimation(View animatedView, View targetView) {
            Point targetPoint = getViewPosition(targetView);
            return animatedView.animate().
                    x(targetPoint.x).
                    y(targetPoint.y).
                    setInterpolator(new AccelerateDecelerateInterpolator()).
                    scaleX(1).
                    scaleY(1).
                    setDuration(RETURN_ANIM_DURATION);
        }

        static ObjectAnimator createFadeOut(ColorDrawable backgroundDrawable) {
            return ObjectAnimator.ofInt(backgroundDrawable, "alpha", backgroundDrawable.getAlpha(), 0);
        }
    }
}
