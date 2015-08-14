package com.la4domain.games.biplanes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.la4domain.games.biplanes.objects.AIBiplane;
import com.la4domain.games.biplanes.objects.Biplane;
import com.la4domain.games.biplanes.objects.Cloud;
import com.la4domain.games.biplanes.objects.HealthBar;
import com.la4domain.games.biplanes.objects.Ground;
import com.la4domain.games.biplanes.objects.Tank;
import com.la4domain.games.biplanes.objects.components.DrawableObject;
import com.la4domain.games.biplanes.objects.components.EntityObject;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainGameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String LOG_TAG = MainGameView.class.getSimpleName();

    private GameLoopThread thread;
    private List collisionList;
    private List objectList;

    private Biplane playerBiplane;
    private Tank[] tanks;
    private Cloud[] clouds;
    private AIBiplane[] biplanes;
    private Ground ground;
    private HealthBar hpBar;
    private Bitmap startGameBitmap;

    private float cameraXPos, cameraYPos; // coordinates of top left corner of the camera
    private static boolean gameState; //true - currently playing, false - start menu

    public MainGameView(Context context) {
        super(context);
        Log.d(LOG_TAG, "MainGameView constructor.");

        getHolder().addCallback(this); // Adding the callback (this) to the surface holder to intercept events

        thread = new GameLoopThread(getHolder(), this);

        setFocusable(true); // Make the MainGameView focusable so it can handle events
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceCreated()");

        if (thread.getState() == Thread.State.NEW) {

            objectList = new LinkedList<DrawableObject>();
            collisionList = new LinkedList<EntityObject>();

            cameraXPos = cameraYPos = 0;
            gameState = false;

            //DEFINING BITMAPS
            Bitmap groundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
            Bitmap tankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
            Bitmap aiBiplaneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aibiplanes);
            Bitmap hpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.healthbar);
            Bitmap biplaneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.biplane);
            startGameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.startgame);

            //OBJECTS CREATION
            playerBiplane = new Biplane(getContext(), collisionList, biplaneBitmap, worldWidth() / 2, worldHeight() / 2);
            ground = new Ground(getContext(), groundBitmap, 0, worldHeight() - groundBitmap.getHeight() / 2);

            tanks = new Tank[Const.T_TANKS_AMOUNT];
            for (int i = 0; i < Const.T_TANKS_AMOUNT; i++) {
                tanks[i] = new Tank(getContext(), collisionList, tankBitmap, worldWidth() / Const.T_TANKS_AMOUNT * i,
                        worldHeight() - (Const.T_HEIGHT * getResources().getDisplayMetrics().density) / 2 - groundBitmap.getHeight() / 2);
            }

            biplanes = new AIBiplane[Const.AI_AMOUNT];
            for (int i = 0; i < Const.AI_AMOUNT; i++) {
                biplanes[i] = new AIBiplane(getContext(), collisionList, aiBiplaneBitmap, getWidth(), getHeight());
            }

            clouds = new Cloud[Const.C_AMOUNT];
            cloudGenerator();

            hpBar = new HealthBar(getContext(), hpBitmap, hpBitmap.getWidth() / 2 + getHeight() / 20, Const.HP_HEIGHT * getResources().getDisplayMetrics().density  / 2 + getHeight() / 20, playerBiplane);

            //FILLING objectList IN RENDERING ORDER
            objectList.add(ground);

            for (int i = 0; i < Const.T_TANKS_AMOUNT; i++) {
                objectList.add(tanks[i]);
            }

            for (int i = 0; i < Const.AI_AMOUNT; i++) {
                objectList.add(biplanes[i]);
            }

            objectList.add(playerBiplane);

            for (int i = 0; i < Const.C_AMOUNT; i++) {
                objectList.add(clouds[i]);
            }

            objectList.add(hpBar);


            //FILLING collisionList
            collisionList.add(playerBiplane);

            for (int i = 0; i < Const.AI_AMOUNT; i++) {
                collisionList.add(biplanes[i]);
            }
            for (int i = 0; i < Const.T_TANKS_AMOUNT; i++) {
                collisionList.add(tanks[i]);
            }

            thread.setRunning(true);
            thread.start();
        } else if (thread.getState() == Thread.State.TERMINATED) {
            thread = new GameLoopThread(getHolder(), this);
            thread.setRunning(true);
            thread.start(); // Start a new thread
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(LOG_TAG, "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceDestroyed()");
        boolean retry = true;

        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Try again shutting down thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!gameState && event.getActionMasked() == MotionEvent.ACTION_UP) {
            MainGameView.gameState = true;
            Bitmap biplaneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.biplane);
            playerBiplane.reset(biplaneBitmap, worldWidth() / 2, worldHeight() / 2);
        }
        else {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                Log.d(LOG_TAG, "ACTION_DOWN: " + event.getX() + " " + event.getY());
                if (event.getX() <= getWidth() / 2 && event.getY() <= getHeight() / 2) {
                    playerBiplane.isSteeringUpwards = true;
                }
                if (event.getX() <= getWidth() / 2 && event.getY() > getHeight() / 2) {
                    playerBiplane.isSteeringDownwards = true;
                }
                if (event.getX() > getWidth() / 2) {
                    playerBiplane.isShooting = true;
                }
            }

            if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                Log.d(LOG_TAG, "ACTION_UP: " + event.getX() + " " + event.getY());

                // touch was released
                if (event.getX() <= getWidth() / 2 && event.getY() <= getHeight() / 2) {
                    playerBiplane.isSteeringUpwards = false;
                }
                if (event.getX() <= getWidth() / 2 && event.getY() > getHeight() / 2) {
                    playerBiplane.isSteeringDownwards = false;
                }
                if (event.getX() > getWidth() / 2) {
                    playerBiplane.isShooting = false;
                }
            }
        }
        return true;
    }

    public void loop(Canvas canvas) {

        ListIterator<DrawableObject> itr = objectList.listIterator();

        while (itr.hasNext()) {
            itr.next().update(canvas);
        }

        cameraXPos = playerBiplane.getXPos() - canvas.getWidth() / 2;
        cameraYPos = playerBiplane.getYPos() - canvas.getHeight() / 2;

        if (cameraYPos > canvas.getHeight() * (Const.W_HEIGHT_COEFF - 1)) {
            cameraYPos = canvas.getHeight() * (Const.W_HEIGHT_COEFF - 1);
        }
    }

    public void render(Canvas canvas) { // May be optimised: not to draw if not in camera
        canvas.drawColor(Color.rgb(0, 210, 255));

        ListIterator<DrawableObject> itr = objectList.listIterator();

        while (itr.hasNext()) {
            itr.next().draw(canvas, cameraXPos, cameraYPos);
        }

        if (!gameState) {
            canvas.drawBitmap(startGameBitmap, getWidth() / 2 - (startGameBitmap.getWidth() / 2), getHeight() / 2- (startGameBitmap.getHeight() / 2), null);
        }
    }

    private void cloudGenerator() {
        Random cloudRandom = new Random();
        Bitmap cloudBitmap = null;


        for (int i = 0; i < Const.C_AMOUNT; i++) {
            int spriteNum = cloudRandom.nextInt(Const.C_SPRITES);
            switch (spriteNum) {
                case 0: {
                    cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
                    break;
                }
                case 1: {
                    cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
                    break;
                }
                case 2: {
                    cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
                    break;
                }
                case 3: {
                    cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud4);
                    break;
                }
            }
            float cloudPosX, cloudPosY, cloudSpeed;
            int cloudDir;

            cloudPosX = worldWidth() * cloudRandom.nextFloat();
            cloudPosY = (worldHeight()  - Const.C_MIN_ALTITUDE) * cloudRandom.nextFloat();

            if (cloudRandom.nextBoolean()) {
                cloudDir = 1;
            }
            else {
                cloudDir = -1;
            }

            cloudSpeed = (Const.C_MAX_SPEED - Const.C_MIN_SPEED) * cloudRandom.nextFloat() + Const.C_MIN_SPEED;

            clouds[i] = new Cloud(getContext(), cloudBitmap, cloudPosX, cloudPosY, cloudDir, cloudSpeed);
        }
    }

    public int worldHeight() {
        return Const.W_HEIGHT_COEFF * getHeight();
    }

    public int worldWidth() {
        return Const.W_WIDTH_COEFF * getWidth();
    }

    public static void setGameState(boolean val) {
        gameState = val;
    }

    public static boolean getGameState() {
        return gameState;
    }
}
