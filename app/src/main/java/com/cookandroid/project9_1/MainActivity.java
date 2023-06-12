package com.cookandroid.project9_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    final static int LINE = 1, CIRCLE = 2;
    static int curShape = LINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(new MyGraphicView(this));
        setTitle("간단 그림판");
    }

    @Override
    // 옵션메뉴를 생성합니다.
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // 두 번째 인수 1이 옵션 메뉴 선택 시 확인할 수 있는 ItemId입니다.
        menu.add(0,1,0,"선 그리기");
        menu.add(0,2,0,"원 그리기");
        return true;
    }

    @Override
    // 옵션 메뉴가 선택되었을 때 호출되는 함수입니다.
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            // 선 그리기 메뉴 선택했을 때
            case 1:
                curShape = LINE;
                return true;

            // 원 그리기 메뉴 선택했을 때
            case 2:
                curShape = CIRCLE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // View 클래스를 상속받아 재정의한 클래스
    private static class MyGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        public MyGraphicView(Context context){
            super(context);
        }

        @Override
        // 화면을 손으로 터치했을 때 호출되는 함수입니다.
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()){

                // 화면에 손을 터치할 때
                case MotionEvent.ACTION_DOWN:
                    startX = (int)event.getX();
                    startY = (int)event.getY();
                    break;

                // 화면에 손을 터치한 체로 움직일 때
                case MotionEvent.ACTION_MOVE:

                    // 화면에서 손을 땔 때
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();

                    // 화면 갱신이 필요할 때 호출하는 함수
                    // 지금 화면은 무효이니 다시 그려주세요라는 의미
                    this.invalidate();
                    break;
            }
            return true;
        }

        @Override
        // onTouchEven 함수에 invalidate()가 있기 때문에
        // ACTION_MOVE와 ACTION_UP이 호출될 때마다 다시 호출되어
        // 그림을 다시 그려줄 것입니다.
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 페인트 객체를 하나 새로 생성해서 설정해줍니다.
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);

            switch(curShape){
                case LINE:
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    break;
                case CIRCLE:

                    // 반지름을 구합니다.
                    // 반지름 = 제곱근 ( 가로 길의의 제곱 + 세로길이의 제곱 )
                    int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) +
                            Math.pow(stopY - startY, 2));

                    canvas.drawCircle(startX,startY,radius,paint);
                    break;
            }
        }
    }
}