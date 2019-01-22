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

public class StandardPostView extends RelativeLayout {

    private static final String logtag = "StandardPostView";

    private View rootView;
    private String mTitle;
    private String mCourse;
    private String mAuthor;
    private String mDate;
    private String mContent;
    private Context mContext;
    private boolean mEye;
    private View.OnClickListener click;

    public StandardPostView(Context context, boolean eye){
        super(context);
        mContext = context;
        mEye = eye;
    }

    public StandardPostView(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
    }

    public void setResVals(MMLSpost currentPost, View.OnClickListener pClick){
        //used after the constructor to configure the texts
        mTitle = currentPost.getTitle();
        mCourse = currentPost.getCourse();
        mAuthor = currentPost.getAuthor();
        mDate = currentPost.getDate();
        mContent = currentPost.getContent();
        click = pClick;
        initView();
    }

    private void initView(){
        TextView txtContent;
        TextView txtCourse;
        TextView txtAuthor;
        TextView txtDate;
        TextView txtTitle;
        ImageView actionButton;

        rootView = inflate(mContext, R.layout.item_standard_post_view, this);

        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtCourse = rootView.findViewById(R.id.txtCourse);
        txtAuthor = rootView.findViewById(R.id.txtAuthor);
        txtDate = rootView.findViewById(R.id.txtDate);
        txtContent = rootView.findViewById(R.id.txtContent);
        actionButton = rootView.findViewById(R.id.imgView);

        txtTitle.setText(mTitle);
        txtCourse.setText(mCourse);
        txtAuthor.setText(mAuthor);
        txtDate.setText(mDate);
        txtContent.setText(mContent);
        txtContent.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD); //REQUIRES API 26 and above. too bad for suilyn >.<
        if(!mEye){
            actionButton.setVisibility(View.INVISIBLE);
        }else{
            actionButton.setOnClickListener(click);
        }

        Log.d(logtag,"initView complete");
    }

    public View getView(){
        return rootView;
    }

}


