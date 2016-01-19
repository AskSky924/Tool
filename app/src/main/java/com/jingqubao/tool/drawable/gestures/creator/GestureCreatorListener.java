package com.jingqubao.tool.drawable.gestures.creator;

import com.jingqubao.tool.drawable.draw.SerializablePath;

public interface GestureCreatorListener {
  void onGestureCreated(SerializablePath serializablePath);

  void onCurrentGestureChanged(SerializablePath currentDrawingPath);
}
