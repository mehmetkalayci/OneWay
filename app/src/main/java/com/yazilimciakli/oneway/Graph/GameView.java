package com.yazilimciakli.oneway.Graph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.yazilimciakli.oneway.Database.DatabaseHandler;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.LevelHelper;
import com.yazilimciakli.oneway.Utils.PaintHelper;
import com.yazilimciakli.oneway.Utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View implements Runnable {


    // pointListesi, oyuncuListesi tanımları
    ArrayList<Tuple<Point, ArrayList<Point>>> pointList = new ArrayList<>();
    ArrayList<Point> playerList = new ArrayList<>();

    //Levelleri getirmekte kullanılır
    LevelHelper levelHelper = new LevelHelper(getContext());

    // Seçili noktanın değerini tutar
    Tuple<Point, ArrayList<Point>> lastPoint = null;

    // Hamle tanımı
    int moveNumber = 0;
    int userMove = 0;

    //Zaman Tanımı
    int time = 0;
    int timerCount = 0;

    //Puan Tanımı
    int score = 0;

    //Level tanımı
    int level = 0;

    //Touch Tolerance tanımı
    float touchTolerance = 0;

    //Level Başlığı tanımı
    String levelName;
    String timeText = getResources().getString(R.string.timeText);
    String moveText = getResources().getString(R.string.moveText);
    String gameOverMessage = getResources().getString(R.string.gameOverMessage);

    //WIDTH
    float width = 0;
    float screenRatio = 0;


    // Grafik tanımı
    PaintHelper paint;

    // Kullanıcıya ait path, kullanıcının göreceği yola ait guidePath ve kullanıcının çizdiği çizgi line olarak tanımlandı
    Path path = new Path();
    Path guidePath = new Path();
    Line line = new Line(0, 0);

    // Oyun kontrol değişkenleri tanımı
    boolean isPointTouched = false;
    boolean isGameOver = false;

    // Oynanan level
    Level currentLevel;

    Handler mHandler = new Handler();

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        mHandler.removeCallbacks(this);
        Intent openLevelIntent = new Intent();

        openLevelIntent.setClass(getContext(), LevelActivity.class);

        getContext().startActivity(openLevelIntent);

        Activity activity = (Activity) getContext();
        activity.overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        return super.dispatchKeyEvent(event);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void setLevel(int level) {
        this.level = level;
        currentLevel = levelHelper.getLevel(level);
        time = currentLevel.time;
        moveNumber = currentLevel.moveNumber;
        userMove = currentLevel.moveNumber;
        levelName = String.format(getResources().getString(R.string.levelName), currentLevel.levelid);

        mHandler.postDelayed(this, 1000);
        addPoints();
        requestLayout();
    }

    public void setWidth(float width) {
        this.width = width;
        screenRatio = width / 480;
        paint = new PaintHelper(getContext(), screenRatio);
        touchTolerance = screenRatio * 20;
        invalidate();
        requestLayout();
    }

    /***
     * Koordinatları pointList'e atar
     */
    void addPoints() {
        List<Point> subPoint = new ArrayList<>();
        for (int i = 0; i < currentLevel.points.size(); i++) {
            float x = currentLevel.points.get(i).x * screenRatio;
            float y = currentLevel.points.get(i).y * screenRatio;
            Point point = new Point(x, y);

            for (int j = 0; j < currentLevel.points.get(i).subpoints.size(); j++) {
                float sx = currentLevel.points.get(i).subpoints.get(j).x * screenRatio;
                float sy = currentLevel.points.get(i).subpoints.get(j).y * screenRatio;
                subPoint.add(new Point(sx, sy));
                guidePath.moveTo(x, y);
                guidePath.lineTo(sx, sy);
            }

            pointList.add(new Tuple<Point, ArrayList<Point>>(
                    point, new ArrayList<Point>(subPoint)
            ));
            subPoint.clear();
        }
    }

    /***
     * Verilen koordinatlardaki Point ve gidebileceği yerleri döndürür
     *
     * @param x X koordinatını girin
     * @param y Y koordinatını girin
     * @return Tuple<Point, ArrayList<Point>>
     */
    Tuple<Point, ArrayList<Point>> findPoint(float x, float y) {

        for (Tuple<Point, ArrayList<Point>> point : pointList) {
            if (Math.abs(x - point.item1.getX()) <= touchTolerance && Math.abs(y - point.item1.getY()) <= touchTolerance) {
                return point;
            }
        }
        return null;
    }

    /***
     * Değerleri sıfırlar
     */
    void reset() {
        path.reset();
        line = new Line(0, 0);
        lastPoint = null;
        isPointTouched = false;
        playerList.clear();
        userMove = currentLevel.moveNumber;
    }

    /***
     * Gidilen rotada önceden çizilmiş bir yol olup olmadığını kontrol eder. (Bir çizgiden ikinci kez geçilmesini engeller.)
     *
     * @param tempItem Hedef Point
     * @param lastItem Başlangıç Point
     * @return Gidilen rotada önceden çizilmiş bir yol varsa true döndürür
     */
    boolean wasThereLine(Point tempItem, Point lastItem) {
        boolean status = false;

        for (int i = 0; i < playerList.size(); i++) {
            int j = i + 1;
            if (j < playerList.size()) {
                if ((playerList.get(i).equals(lastItem) && playerList.get(j).equals(tempItem)) || (playerList.get(i).equals(tempItem) && playerList.get(j).equals(lastItem))) {
                    return true;
                }
            }
        }

        return status;
    }

    /***
     * Girilen metnin, belirtilen paint nesnesiyle çizildiğinde oluşan genişliğini verir.
     *
     * @param text  Genişliği hesaplanacak metnini girin
     * @param paint Metni çizeceğiniz Paint nesnesini girin
     * @return Toplam genişlik
     */
    float getSizeOfText(String text, Paint paint) {
        float[] sizeOfLetters = new float[text.length()];
        paint.getTextWidths(text, sizeOfLetters);

        float sum = 0;
        for (float size : sizeOfLetters) {
            sum += size;
        }
        return sum;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Pointleri çiz
        for (Tuple<Point, ArrayList<Point>> point : pointList) {
            canvas.drawCircle(point.item1.getX(), point.item1.getY(), screenRatio * 15, paint.circlePaint());
        }

        // Path çiz
        canvas.drawPath(path, paint.pathLine());
        canvas.drawPath(guidePath, paint.guideLine());

        // Time Progressbar
        canvas.drawLine(screenRatio * 40, canvas.getHeight() - (screenRatio * 40), width - (screenRatio * 40) - timerCount, canvas.getHeight() - (screenRatio * 40), paint.pathLine());
        canvas.drawLine(screenRatio * 40, canvas.getHeight() - (screenRatio * 40), width - (screenRatio * 40), canvas.getHeight() - (screenRatio * 40), paint.pathLittleAlpa());

        /********* Game Text Başlangıç *********/
        float moveTextSize = getSizeOfText(moveText, paint.borderText());
        float timeXCoordinate = getSizeOfText(timeText, paint.borderText()) / 2;
        float moveXCoordinate = moveTextSize / 2;

        canvas.drawText(levelName, (canvas.getWidth() / 2) - (screenRatio * 35), screenRatio * 45, paint.titleText());
        canvas.drawText(timeText, screenRatio * 20, screenRatio * 45, paint.borderText());
        canvas.drawText(moveText, width - moveTextSize - (screenRatio * 20), screenRatio * 45, paint.borderText());

        float timeXbottom = getSizeOfText(String.valueOf(time), paint.borderMiddleText()) / 2;
        float moveXbottom = getSizeOfText(String.valueOf(userMove), paint.borderMiddleText()) / 2;

        canvas.drawText(String.valueOf(time), (screenRatio * 20) + timeXCoordinate - timeXbottom, screenRatio * 75, paint.borderMiddleText());
        canvas.drawText(String.valueOf(userMove), width - (moveTextSize + (screenRatio * 20) - moveXCoordinate + moveXbottom), screenRatio * 75, paint.borderMiddleText());
        /********* /Game Text Bitiş *********/


        // Parmağın bulunduğu yere çizgi çiz
        canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paint.circlePaint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Oyun bittiyse çizmeyi engelle
        if (isGameOver) {
            return false;
        }

        // Parmağın bulunduğu X,Y konumunu al
        float x = event.getX();
        float y = event.getY();
        // Parmağın bulunduğu konumdaki Point'i bulur
        Tuple<Point, ArrayList<Point>> tempPoint = findPoint(x, y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (tempPoint != null) {

                    // Çizgi başlangıç konumunu, belirlenen point'e göre ayarlar
                    line.setX1(tempPoint.item1.getX());
                    line.setY1(tempPoint.item1.getY());
                    line.setX2(tempPoint.item1.getX());
                    line.setY2(tempPoint.item1.getY());

                    // Çizimin başlangıç noktasını, ilk seçilen Point'e göre ayarlar
                    path.moveTo(tempPoint.item1.getX(), tempPoint.item1.getY());

                    // Başlangıç noktasını lastPointte tut
                    lastPoint = findPoint(tempPoint.item1.getX(), tempPoint.item1.getY());

                    // playerList'e başlangıç noktasını ata
                    playerList.add(lastPoint.item1);

                    // Tıklandığını sisteme bildir ve hareket mekanızmasını aktive et
                    isPointTouched = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isPointTouched) {
                    // Çizgiyi boyutlandırır
                    line.setX2(x);
                    line.setY2(y);

                    // tempPoint(HedefNokta), lastPoint(başlangıç noktası) bağlanabilir mi?
                    if (tempPoint != null && lastPoint != null && lastPoint.item2.contains(tempPoint.item1) && !wasThereLine(tempPoint.item1, lastPoint.item1)) {

                        // tempPoint(HedefNokta), lastPoint(başlangıç noktası) bağlanabilir olduğuna göre;
                        // playerList'e tempPoint'i (hedef noktayı) atar
                        playerList.add(tempPoint.item1);

                        // lastPoint'i (Başlangıç noktasını), tempPoint(hedef nokta) ile değiştir
                        lastPoint = tempPoint;
                        // Çizgi başlangıç noktasını değiştir
                        line.setX1(lastPoint.item1.getX());
                        line.setY1(lastPoint.item1.getY());
                        line.setX2(lastPoint.item1.getX());
                        line.setY2(lastPoint.item1.getY());

                        // Yol çiz
                        userMove--;
                        path.lineTo(tempPoint.item1.getX(), tempPoint.item1.getY());
                        // Oyunu bitirdiyse
                        if (moveNumber == playerList.size() - 1) {
                            isGameOver = true;
                            DatabaseHandler dbHandler;
                            dbHandler = new DatabaseHandler(getContext());

                            com.yazilimciakli.oneway.Database.Level tempData= dbHandler.getLevel(currentLevel.levelid);

                            Log.d("deneme",tempData.toString());
                            if(tempData!=null) {
                                if(tempData.getScore()==0 && tempData.getElapsedTime()==0)
                                {
                                    com.yazilimciakli.oneway.Database.Level levels=new com.yazilimciakli.oneway.Database.Level();
                                    com.yazilimciakli.oneway.Database.Level level2=new com.yazilimciakli.oneway.Database.Level();
                                    levels.setLevelId(currentLevel.levelid);
                                    levels.setElapsedTime(time);
                                    levels.setScore(currentLevel.score);
                                    dbHandler.updateLevel(levels);
                                    level2.setLevelId(currentLevel.levelid+1);
                                    dbHandler.addLevel(level2);
                                }
                                else
                                {
                                    com.yazilimciakli.oneway.Database.Level levels=new com.yazilimciakli.oneway.Database.Level();
                                    com.yazilimciakli.oneway.Database.Level level2=new com.yazilimciakli.oneway.Database.Level();
                                    levels.setLevelId(currentLevel.levelid);
                                    levels.setElapsedTime(time);
                                    levels.setScore(currentLevel.score);
                                    dbHandler.updateLevel(levels);
                                    level2.setLevelId(currentLevel.levelid+1);
                                    dbHandler.addLevel(level2);
                                }
                            }
                            else {
                                com.yazilimciakli.oneway.Database.Level levels=new com.yazilimciakli.oneway.Database.Level();
                                com.yazilimciakli.oneway.Database.Level level2=new com.yazilimciakli.oneway.Database.Level();
                                levels.setLevelId(currentLevel.levelid);
                                levels.setElapsedTime(time);
                                levels.setScore(currentLevel.score);
                                dbHandler.addLevel(levels);
                                level2.setLevelId(currentLevel.levelid+1);
                                dbHandler.addLevel(level2);
                            }

                            WinDialog winDialog = new WinDialog(getContext(), levelName, String.valueOf(time), String.valueOf(currentLevel.score),currentLevel.levelid);
                            winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            winDialog.show();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // Oyuncu parmağını kaldırırsa, tekrar başlat
                reset();
                break;
        }

        invalidate();
        return true;
    }

    @Override
    public void run() {
        if (!isGameOver) {
            time--;
            timerCount += (width - ((screenRatio * 40) * 2)) / currentLevel.time;
            invalidate();
            mHandler.postDelayed(this, 1000);
        }
        if (time == 0 &&  !isGameOver) {
            isGameOver = true;
            GameOverDialog gameoverDialog = new GameOverDialog(getContext(),currentLevel.levelid);
            gameoverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            gameoverDialog.show();
        }
    }
}