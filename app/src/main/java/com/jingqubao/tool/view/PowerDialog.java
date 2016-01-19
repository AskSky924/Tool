package com.jingqubao.tool.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jingqubao.tool.CompassActivity;
import com.jingqubao.tool.MainActivity;
import com.jingqubao.tool.R;
import com.jingqubao.tool.TextShowActivity;
import com.jingqubao.tool.game_2048.Main2048Activity;
import com.jingqubao.tool.util.Consts;

import java.io.File;
import java.io.FileReader;


public class PowerDialog extends DialogFragment {

    public PowerDialog() {

    }

    LinearLayout mLog, mSystemLog, mClearLog, compass, game2048, safemode;
    FrameLayout frame, frame2;
    private CircularRevealView revealView;
    private View selectedView;
    private int backgroundColor;
    ProgressBar progress;
    TextView status, status_detail;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_power, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        backgroundColor = Color.parseColor("#ffffff");
        mLog = (LinearLayout) view.findViewById(R.id.my_log);
        mSystemLog = (LinearLayout) view.findViewById(R.id.system_log);
        mClearLog = (LinearLayout) view.findViewById(R.id.clear_log);
        compass = (LinearLayout) view.findViewById(R.id.compass);
        game2048 = (LinearLayout) view.findViewById(R.id.game_2048);
        safemode = (LinearLayout) view.findViewById(R.id.safemode);

        frame = (FrameLayout) view.findViewById(R.id.frame);
        frame2 = (FrameLayout) view.findViewById(R.id.frame2);

        status = (TextView) view.findViewById(R.id.status);
        status_detail = (TextView) view.findViewById(R.id.status_detail);

        progress = (ProgressBar) view.findViewById(R.id.progress);


        progress.getIndeterminateDrawable().setColorFilter(
                Color.parseColor("#ffffff"),
                android.graphics.PorterDuff.Mode.SRC_IN);

        mSystemLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#3f51b5");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");

                String str = getString(Consts.LOG_PATH + "/" + Consts.SYSTEM_LOG);
                Intent intent = new Intent(getActivity(), TextShowActivity.class);
                intent.putExtra("title", "系统日志");
                intent.putExtra("desc", str);
                startActivity(intent);
            }
        });
        mLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#d32f2f");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");

                String str = getString(Consts.LOG_PATH + "/" + Consts.MY_LOG);
                Intent intent = new Intent(getActivity(), TextShowActivity.class);
                intent.putExtra("title", "我的日志");
                intent.putExtra("desc", str);
                startActivity(intent);
            }
        });
        mClearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#e91e63");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");

                File file = new File(Consts.LOG_PATH + "/" + Consts.MY_LOG);
                file.delete();
                file = new File(Consts.LOG_PATH + "/" + Consts.SYSTEM_LOG);
                file.delete();
                Toast.makeText(getActivity(), "清除完成！", Toast.LENGTH_LONG).show();
            }
        });
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#8bc34a");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");
                Intent intent = new Intent(getActivity(), CompassActivity.class);
                startActivity(intent);
            }
        });
        game2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#277b71");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");
                Intent intent = new Intent(getActivity(), Main2048Activity.class);
                startActivity(intent);
            }
        });
        safemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final int color = Color.parseColor("#009688");
//                final Point p = getLocationInView(revealView, v);
//
//                if (selectedView == v) {
//                    revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
//                    selectedView = null;
//                } else {
//                    revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
//                    selectedView = v;
//                }
//
//                ((MainActivity) getActivity()).revealFromTop();
//                frame.setVisibility(View.GONE);
//                frame2.setVisibility(View.VISIBLE);
//
//                status.setText("本功能暂未开通");
//                status_detail.setText("本功能暂未开通...");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://fir.im/lalalademaxiya"));
                startActivity(intent);
            }
        });
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity != null && activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    private String getString(String path) {
        StringBuffer str = new StringBuffer();
        try {
            FileReader r = new FileReader(path);
            char[] buffer = new char[1024];
            int num;
            while ((num = r.read(buffer)) != -1) {
                str.append(new String(buffer, 0, num));
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return str.toString();
    }
}
