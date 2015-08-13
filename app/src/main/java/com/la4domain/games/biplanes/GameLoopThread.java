package com.la4domain.games.biplanes;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {

    private static final String LOG_TAG = GameLoopThread.class.getSimpleName();

    private SurfaceHolder surfaceHolder;
    private MainGameView gameView;

    private boolean running;

    public GameLoopThread(SurfaceHolder surfaceHolder, MainGameView gameView) {
        super();
        Log.d(LOG_TAG, "GameLoopThread constructor.");
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        Log.d(LOG_TAG, "GameLoopThread: run()");

        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    this.gameView.loop(canvas);
                    this.gameView.render(canvas);
                }

            } catch (NullPointerException e) {
                    running = false;
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Caught NullPointerException");

            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }

        Log.d(LOG_TAG, "Not running anymore.");
    }
}

