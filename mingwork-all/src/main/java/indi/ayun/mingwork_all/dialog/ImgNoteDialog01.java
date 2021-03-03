package indi.ayun.mingwork_all.dialog;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;
import com.facebook.drawee.view.SimpleDraweeView;

import indi.ayun.mingwork_all.base.BaseDialog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

public class ImgNoteDialog01 extends BaseDialog {
    private Dialog mDialog;
    private static ImgNoteDialog01 imgNoteDialog01;

    private ImageView closeView;
    private TextView txtView;
    private SimpleDraweeView imgView;
    private Button button;

    private String TAG;
    private String imgPath, txt;

    public ImgNoteDialog01(String txt, String imgPath, String tag) {
        TAG = tag;
        this.txt = txt;
        this.imgPath = imgPath;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_note_img01, null);
        run(layout);
    }

    public ImgNoteDialog01(int resource, String tag) {
        TAG = tag;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public ImgNoteDialog01(int resource, String txt, String imgPath, String tag) {
        TAG = tag;
        this.txt = txt;
        this.imgPath = imgPath;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }


    private void run(View layout) {
        closeView = layout.findViewById(R.id.dialog_noteImg_x);
        closeView.setOnClickListener(new CloseDialog());
        txtView = layout.findViewById(R.id.dialog_noteImg_txt);
        if (IsNothing.onAnything(txt))txtView.setText(txt);
        imgView=layout.findViewById(R.id.dialog_noteImg_img);
        if (IsNothing.onAnything(imgPath))imgView.setImageURI(Uri.parse(imgPath));
        button=layout.findViewById(R.id.dialog_noteImg_but);
        button.setOnClickListener(new Ok());

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(false,0);
    }

    public static void show(String txt, String img, String tag) {
        if (imgNoteDialog01 != null) {
            imgNoteDialog01.dismiss();
        }
        imgNoteDialog01 = null;
        imgNoteDialog01 = new ImgNoteDialog01(txt, img, tag);
        imgNoteDialog01.show();
    }

    public static void show(int resource,String tag) {
        if (imgNoteDialog01 != null) {
            imgNoteDialog01.dismiss();
        }
        imgNoteDialog01 = null;
        imgNoteDialog01 = new ImgNoteDialog01(resource, tag);
        imgNoteDialog01.show();
    }

    public static void show(int resource, String txt, String img, String tag) {
        if (imgNoteDialog01 != null) {
            imgNoteDialog01.dismiss();
        }
        imgNoteDialog01 = null;
        imgNoteDialog01 = new ImgNoteDialog01(resource, txt, img, tag);
        imgNoteDialog01.show();
    }


    class CloseDialog implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type", TAG+"_Close");
            getListener().onFragmentInteraction(bundle);
            dismiss();
        }
    }
    class Ok implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type", TAG+"_Ok");
            getListener().onFragmentInteraction(bundle);
            dismiss();
        }
    }
}
