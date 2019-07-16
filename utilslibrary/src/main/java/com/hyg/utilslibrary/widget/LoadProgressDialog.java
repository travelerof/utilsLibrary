package com.hyg.utilslibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hyg.utilslibrary.R;


public class LoadProgressDialog extends Dialog {


    private Context mContext;
    private TextView tvMessage;
    private MiniLoadingView loadingView;

    public LoadProgressDialog(@NonNull Context context) {
        super(context,R.style.LoadProgressDialog);
        init(context);
    }

    public LoadProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {

        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_load_progress,null);
        loadingView = view.findViewById(R.id.dialog_load_info_miniloadingview);
        tvMessage = view.findViewById(R.id.dialog_load_info_textview);
        setCancelable(false);
        setContentView(view);
    }

    public LoadProgressDialog setMessage(String msg){

        tvMessage.setText(msg);
        return this;
    }


}
