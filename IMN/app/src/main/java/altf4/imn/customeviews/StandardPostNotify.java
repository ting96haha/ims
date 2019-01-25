package altf4.imn.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import altf4.imn.R;
import mmls.MMLSpost;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class StandardPostNotify extends RelativeLayout {

    private static final String logtag = "StandardPostView";

    private View rootView;
    private String mTitle;
    private String mContent;
    private Context mContext;
    private boolean mEye;
    private OnClickListener click;

    public StandardPostNotify(Context context){
        super(context);
        mContext = context;
    }

    public StandardPostNotify(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
    }

    public void setResVals(MMLSpost currentPost, OnClickListener pClick){
        //used after the constructor to configure the texts
        mTitle = currentPost.getTitle();
        mContent = currentPost.getContent();
        //click = pClick;
        initView();
    }

    private void initView(){
        TextView txtContent;
        TextView txtTitle;
        ImageView actionButton;

        rootView = inflate(mContext, R.layout.notification_post, this);

        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtContent = rootView.findViewById(R.id.txtContent);
        //actionButton = rootView.findViewById(R.id.imgView);

        txtTitle.setText(mTitle);
        txtContent.setText(mContent);
        txtContent.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD); //REQUIRES API 26 and above. too bad for suilyn >.<
        /*
        if(!mEye){
            actionButton.setVisibility(View.INVISIBLE);
        }else{
            actionButton.setOnClickListener(click);
        }
        */

        Log.d(logtag,"initView complete");
    }

    public View getView(){
        return rootView;
    }

}


