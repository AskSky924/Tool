package com.jingqubao.tool.drawable.draw.log;

import android.graphics.Canvas;
import android.graphics.RectF;

public interface CanvasLogger {
  void log(Canvas canvas, RectF canvasRect, RectF viewRect, float scaleFactor);
}
