package altf4.imn.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import altf4.imn.R;

public class StandardPostView extends RelativeLayout {

    private static final String logtag = "StandardPostView";

    private View rootView;
    private String mTitle;
    private String mContent;
    private Context mContext;

    public StandardPostView(Context context){
        super(context);
        mContext = context;
    }

    public StandardPostView(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
    }

    public void setResVals(String pTitle,String pContent){
        //used after the constructor to configure the texts
        mTitle = pTitle;
        mContent = pContent;
        initView();
    }

    private void initView(){
        TextView txtContent;
        TextView txtTitle;
        ImageView actionButton;

        rootView = inflate(mContext, R.layout.item_standard_post_view, this);

        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtContent = rootView.findViewById(R.id.txtContent);
        actionButton = rootView.findViewById(R.id.imgView);

        txtTitle.setText(mTitle);
        txtContent.setText(mContent);
        //txtContent.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD); REQUIRES API 26 and above. too bad for suilyn >.<


        actionButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                //donothing for now
                //TODO: Set this up
            }
        });
        Log.d(logtag,"initView complete");
    }

    public View getView(){
        return rootView;
    }

}


