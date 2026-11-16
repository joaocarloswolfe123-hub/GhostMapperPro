package com.ghostmapper.pro;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.*;
import android.widget.*;

public class OverlayService extends Service {
    private WindowManager manager; private View panel;
    @Override public void onCreate() {
        super.onCreate(); manager = (WindowManager)getSystemService(WINDOW_SERVICE);
        LinearLayout layout = new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL); layout.setPadding(20,16,20,16); layout.setBackgroundColor(0xee4c45bd);
        TextView header = new TextView(this); header.setText("GhostMapper • Editor"); header.setTextSize(17); header.setTextColor(Color.WHITE); layout.addView(header);
        TextView hint = new TextView(this); hint.setText("Arraste o painel para mover.\nEditor demonstrativo: sem injeção de toques."); hint.setTextColor(Color.WHITE); layout.addView(hint);
        Button close = new Button(this); close.setText("Fechar"); close.setOnClickListener(v -> stopSelf()); layout.addView(close);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-2,-2,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START; params.x=35; params.y=140;
        header.setOnTouchListener(new View.OnTouchListener() { float sx,sy; int px,py;
            @Override public boolean onTouch(View v, android.view.MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){ sx=event.getRawX();sy=event.getRawY();px=params.x;py=params.y;return true; }
                if(event.getAction()==MotionEvent.ACTION_MOVE){ params.x=px+(int)(event.getRawX()-sx);params.y=py+(int)(event.getRawY()-sy);manager.updateViewLayout(panel,params);return true; }
                return event.getAction()==MotionEvent.ACTION_UP;
            }
        });
        panel=layout; manager.addView(panel,params);
    }
    @Override public void onDestroy(){ if(panel!=null)manager.removeView(panel);super.onDestroy(); }
    @Override public IBinder onBind(Intent intent){ return null; }
}
