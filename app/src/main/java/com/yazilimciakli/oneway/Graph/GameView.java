package com.yazilimciakli.oneway.Graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.Level.GetLevels;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameView extends View{


    // pointListesi, oyuncuListesi tanımları
    ArrayList<Tuple<Point, ArrayList<Point>>> pointList = new ArrayList<>();
    ArrayList<Point> playerList = new ArrayList<>();

    //Levelleri getirmekte kullanılır
    GetLevels levels=new GetLevels(getContext());

    // Seçili noktanın değerini tutar
    Tuple<Point, ArrayList<Point>> lastPoint = null;

    // Hamle tanımı
    int moveNumber = 0;

    //Zaman Tanımı
    int time;

    //Puan Tanımı
    int score=0;

    //level tanımı
    int level=0;

    //Level Başlığı tanımı
    String name;

    //WIDTH
    double width=0;
    double ballance=0;


    // Grafik tanımı
    Paint paint = new Paint();
    //Text Paint
    Paint textPaint = new Paint();

    Path path = new Path();
    Path guidePath=new Path();
    Line line = new Line(0, 0);

    // Oyun kontrol değişkenleri tanımı
    boolean isPointTouched = false;
    boolean isGameOver = false;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.rgb(31, 31, 33));

        level=GameActivity.LEVEL;
        width=GameActivity.WIDTH;
        ballance=width/480;

        addPoints(level);
        setupPaints();

        moveNumber=levels.getLevels(level).moveNumber;

        score=levels.getLevels(level).score;
        time=levels.getLevels(level).time;
        name=levels.getLevels(level).name;

    }


    ArrayList<Tuple<Point, ArrayList<Point>>> getLevels(int levelId) {

        ArrayList<Tuple<Point, ArrayList<Point>>> tempList = new ArrayList<>();

        List<String> levels = Arrays.asList(this.getResources().getStringArray(R.array.levels));
        String currentLevel = levels.get(levelId);

        return tempList;
    }

    /***
     * Paint değişkeninin genel özelliklerini atar
     */
    void setupPaints() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((int)ballance*5);
        paint.setColor(Color.rgb(255, 255, 255));

        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        Typeface plain = Typeface.createFromAsset(getResources().getAssets(),"fonts/Mathlete-Bulky.otf");

        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.rgb(255, 255, 255));
        textPaint.setFakeBoldText(true);
        textPaint.setTypeface(plain);
    }


    /***
     * Koordinatları pointList'e atar
     * */
    void addPoints(int levelNumber) {

        List<Point> subPoint=new ArrayList<>();
        for(int i=0;i<levels.getLevels(levelNumber).points.size();i++)
        {
            float x=(float)(levels.getLevels(levelNumber).points.get(i).x*ballance);
            float y=(float)(levels.getLevels(levelNumber).points.get(i).y*ballance);
            Point point=new Point(x,y);

            for(int a=0;a<levels.getLevels(levelNumber).points.get(i).subpoints.size();a++)
            {
                float sx=(float)(levels.getLevels(levelNumber).points.get(i).subpoints.get(a).x*ballance);
                float sy=(float)(levels.getLevels(levelNumber).points.get(i).subpoints.get(a).y*ballance);
                subPoint.add(new Point(sx,sy));
                guidePath.moveTo(x,y);
                guidePath.lineTo(sx,sy);

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
            canvas.drawCircle(point.item1.getX(), point.item1.getY(), (int)ballance*15, paint);
        }

        // Path çiz
        paint.setStrokeWidth((int)ballance*5);
        canvas.drawPath(path, paint);
        paint.setStrokeWidth((int)ballance*1);
        canvas.drawPath(guidePath, paint);
        paint.setStrokeWidth((int)ballance*5);
        /*Game Text Başlangıç*/
        textPaint.setTextSize((int)ballance*50);
        canvas.drawText(name,canvas.getWidth()/2-80,80,textPaint);
        textPaint.setTextSize((int)ballance*40);

        //Text
        String timeText="Time";
        String moveText="Move";

        canvas.drawText(timeText,20,60,textPaint);
        canvas.drawText(moveText,(int) width-(int)ballance*60,60,textPaint);
        textPaint.setTextSize((int)ballance*30);
        textPaint.getTextAlign();

        canvas.drawText(String.valueOf(time),timeText.length()/2*15,120,textPaint);
        canvas.drawText(String.valueOf(moveNumber),(int) canvas.getWidth()-moveText.length()/2*15,120,textPaint);
        /*Game Text Bitiş*/

        // Seçilince çıkan çizgiyi çiz
        //paint.setARGB(90, 255, 0, 0);
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