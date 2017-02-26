package com.yazilimciakli.oneway.Graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameView extends View{


    // pointListesi, oyuncuListesi tanımları
    ArrayList<Tuple<Point, ArrayList<Point>>> pointList = new ArrayList<>();
    ArrayList<Point> playerList = new ArrayList<>();

    // Seçili noktanın değerini tutar
    Tuple<Point, ArrayList<Point>> lastPoint = null;

    // Hamle tanımı
    int moveNumber = 8;

    // Grafik tanımı
    Paint paint = new Paint();
    Path path = new Path();
    Line line = new Line(0, 0);

    // Oyun kontrol değişkenleri tanımı
    boolean isPointTouched = false;
    boolean isGameOver = false;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(0xFFFFFFFF);


        //addPoints();
        setupPaints();
    }

    ArrayList<Tuple<Point, ArrayList<Point>>> getLevels(int levelId) {

        ArrayList<Tuple<Point, ArrayList<Point>>> tempList = new ArrayList<>();

        List<String> levels = Arrays.asList(getResources().getStringArray(R.array.levels));
        String currentLevel = levels.get(levelId);

        currentLevel.split(".");


        return tempList;
    }


    /***
     * Paint değişkeninin genel özelliklerini atar
     */
    void setupPaints() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }


    /***
     * Koordinatları pointList'e atar
     * */
    void addPoints() {
        pointList.add(new Tuple<Point, ArrayList<Point>>(
                new Point(500,100),
                new ArrayList<Point>(Arrays.asList(
                        new Point(200,300),
                        new Point(800,300)
                ))
        ));

        pointList.add(new Tuple<Point, ArrayList<Point>>(
                new Point(800,300),
                new ArrayList<Point>(Arrays.asList(
                        new Point(500,100),
                        new Point(800,700),
                        new Point(200,700),
                        new Point(200,300)
                ))
        ));

        pointList.add(new Tuple<Point, ArrayList<Point>>(
                new Point(800,700),
                new ArrayList<Point>(Arrays.asList(
                        new Point(800,300),
                        new Point(200,300),
                        new Point(200,700)
                ))
        ));

        pointList.add(new Tuple<Point, ArrayList<Point>>(
                new Point(200,700),
                new ArrayList<Point>(Arrays.asList(
                        new Point(800,700),
                        new Point(200,300),
                        new Point(800,300)
                ))
        ));

        pointList.add(new Tuple<Point, ArrayList<Point>>(
                new Point(200,300),
                new ArrayList<Point>(Arrays.asList(
                        new Point(500,100),
                        new Point(200,700),
                        new Point(800,300),
                        new Point(800,700)
                ))
        ));
    }


    /***
     * Verilen koordinatlardaki Point ve gidebileceği yerleri döndürür
     * @param x X koordinatını girin
     * @param y Y koordinatını girin
     * @return Tuple<Point, ArrayList<Point>>
     */
    Tuple<Point, ArrayList<Point>> findPoint(float x, float y) {
        int touchTolerance = 35;

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

    @Override
    protected void onDraw(Canvas canvas) {

        // Pointleri çiz
        for (Tuple<Point, ArrayList<Point>> point: pointList) {
            canvas.drawCircle(point.item1.getX(), point.item1.getY(), 30, paint);
        }

        // Path çiz
        canvas.drawPath(path, paint);

        // Seçilince çıkan çizgiyi çiz
        paint.setARGB(90, 255, 0, 0);
        canvas.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), paint);
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
}