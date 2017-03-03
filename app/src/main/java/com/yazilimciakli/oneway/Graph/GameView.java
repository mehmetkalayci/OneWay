package com.yazilimciakli.oneway.Graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.Utils.LevelHelper;
import com.yazilimciakli.oneway.Utils.PaintHelper;
import com.yazilimciakli.oneway.Utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View implements Runnable{


    // pointListesi, oyuncuListesi tanımları
    ArrayList<Tuple<Point, ArrayList<Point>>> pointList = new ArrayList<>();
    ArrayList<Point> playerList = new ArrayList<>();

    //Levelleri getirmekte kullanılır
    LevelHelper levelHelper = new LevelHelper(getContext());

    // Seçili noktanın değerini tutar
    Tuple<Point, ArrayList<Point>> lastPoint = null;

    // Hamle tanımı
    int moveNumber = 0;
    int moveCon = 0;

    //Zaman Tanımı
    int time;
    int timerCount=0;

    //Puan Tanımı
    int score = 0;

    //level tanımı
    int level = 0;

    //Touch Tolerance tanımı
    float touchTolerance = 0;

    //Level Başlığı tanımı
    String name;

    //WIDTH
    float width = 0;
    float ballance = 0;


    // Grafik tanımı
    PaintHelper paint;

    Path path = new Path();
    Path guidePath = new Path();
    Line line = new Line(0, 0);

    // Oyun kontrol değişkenleri tanımı
    boolean isPointTouched = false;
    boolean isGameOver = false;

    // Oynanan level
    Level currentLevel = levelHelper.getLevel(level);

    Handler mHandler = new Handler();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.rgb(31, 31, 33));

        level = GameActivity.LEVEL;
        width = GameActivity.WIDTH;
        ballance = width / 480;

        paint=new PaintHelper(getContext(),ballance);

        touchTolerance = ballance * 20;

        mHandler.postDelayed(this,1000);

        addPoints();

        moveNumber = currentLevel.moveNumber;
        moveCon=currentLevel.moveNumber;
        score = currentLevel.score;
        time = currentLevel.time;
        name = currentLevel.name;
    }


    /***
     * Koordinatları pointList'e atar
     * */
    void addPoints() {
        List<Point> subPoint = new ArrayList<>();
        for(int i = 0; i < currentLevel.points.size(); i++)
        {
            float x = currentLevel.points.get(i).x * ballance;
            float y = currentLevel.points.get(i).y * ballance;
            Point point = new Point(x,y);

            for(int j = 0; j < currentLevel.points.get(i).subpoints.size(); j++)
            {
                float sx = currentLevel.points.get(i).subpoints.get(j).x*ballance;
                float sy = currentLevel.points.get(i).subpoints.get(j).y*ballance;
                subPoint.add(new Point(sx, sy));
                guidePath.moveTo(x, y);
                guidePath.lineTo(sx, sy);
            }

            pointList.add(new Tuple<Point, ArrayList<Point>>(
                    point,new ArrayList<Point>(subPoint)
            ));
            subPoint.clear();
        }
    }

    /***
     * Verilen koordinatlardaki Point ve gidebileceği yerleri döndürür
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
        return  null;
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
        moveCon=currentLevel.moveNumber;
    }

    /***
     * Gidilen rotada önceden çizilmiş bir yol olup olmadığını kontrol eder. (Bir çizgiden ikinci kez geçilmesini engeller.)
     * @param tempItem Hedef Point
     * @param lastItem Başlangıç Point
     * @return Gidilen rotada önceden çizilmiş bir yol varsa true döndürür
     */
    boolean wasThereLine(Point tempItem, Point lastItem) {
        boolean status  = false;

        for (int i = 0; i < playerList.size(); i++) {
            int j = i+1;
            if (j < playerList.size()) {
                if ((playerList.get(i).equals(lastItem)  && playerList.get(j).equals(tempItem)) ||  (playerList.get(i).equals(tempItem)  && playerList.get(j).equals(lastItem))){
                    return true;
                }
            }
        }

        return status;
    }

    /***
     * Girilen metnin, belirtilen paint nesnesiyle çizildiğinde oluşan genişliğini verir.
     * @param text Genişliği hesaplanacak metnini girin
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
        for (Tuple<Point, ArrayList<Point>> point: pointList) {
            canvas.drawCircle(point.item1.getX(), point.item1.getY(), ballance * 15, paint.circlePaint());
        }

        // Path çiz
        canvas.drawPath(path, paint.pathLine());
        canvas.drawPath(guidePath, paint.guideLine());

        canvas.drawLine(ballance*40,canvas.getHeight()-(ballance*40),width-(ballance*40)-timerCount,canvas.getHeight()-(ballance*40), paint.pathLine());
        canvas.drawLine(ballance*40,canvas.getHeight()-(ballance*40),width-(ballance*40),canvas.getHeight()-(ballance*40), paint.pathLittleAlpa());

        /* Game Text Başlangıç */
        canvas.drawText(name,(canvas.getWidth() / 2)-(ballance * 35), ballance * 45, paint.titleText());


        // Text
        String timeText = "Time";
        String moveText = "Move";

        canvas.drawText(timeText, ballance * 20, ballance * 45, paint.borderText());
        canvas.drawText(moveText, width - ballance * 60, ballance * 45, paint.borderText());

        // Yazının ortasını bulduk getSizeOfText(timeText, textPaint) / 2 ve nesnenin ortasını aldık (ballance * 20 / 2)
        float timeXCoordinate = getSizeOfText(timeText, paint.borderText()) / 2;
        float moveXCoordinate = getSizeOfText(moveText, paint.borderText()) / 2;

        float timeXbottom=getSizeOfText(String.valueOf(time), paint.borderMiddleText())/2;
        float moveXbottom=getSizeOfText(String.valueOf(moveCon), paint.borderMiddleText())/2;

        //textPaint.getTextAlign();
        //canvas.drawText(String.valueOf(time), ballance * 20, ballance * 75, textPaint);
        //canvas.drawText(String.valueOf(moveNumber), width - ballance * 60, ballance * 75, textPaint);
        canvas.drawText(String.valueOf(time), (ballance*20)+timeXCoordinate-timeXbottom, ballance * 75, paint.borderMiddleText());
        canvas.drawText(String.valueOf(moveCon), width -((ballance * 60)- moveXCoordinate+moveXbottom), ballance * 75, paint.borderMiddleText());

        // Seçilince çıkan çizgiyi çiz
        //paint.setARGB(90, 255, 0, 0);
        canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paint.circlePaint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Oyun bittiyse çizmeyi engelle
        if (isGameOver) {
            return false;
        }

        // Parmağın bulunduğu X-Y konumunu al
        float x = event.getX();
        float y = event.getY();
        // Parmağın bulunduğu konumdaki Point'i bulur
        Tuple<Point, ArrayList<Point>> tempPoint = findPoint(x, y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(tempPoint != null) {

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
                        moveCon--;
                        path.lineTo(tempPoint.item1.getX(), tempPoint.item1.getY());
                        // Oyunu bitirdiyse
                        if (moveNumber == playerList.size()-1) {
                            isGameOver = true;
                            Toast.makeText(getContext(), "You Win!", Toast.LENGTH_SHORT).show();
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
        if(isGameOver==false)
        {
            time--;
            timerCount+=(width-((ballance*40)*2))/currentLevel.time;
            this.invalidate();
            mHandler.postDelayed(this, 1000);
        }
        if(time==0)
        {
            isGameOver=true;
            Toast.makeText(getContext(), "Game Over :(", Toast.LENGTH_SHORT).show();
        }
    }
}