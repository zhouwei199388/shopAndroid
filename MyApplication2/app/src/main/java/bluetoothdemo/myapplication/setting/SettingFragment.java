package bluetoothdemo.myapplication.setting;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

import bluetoothdemo.myapplication.MainActivity;
import bluetoothdemo.myapplication.MyApplication;
import bluetoothdemo.myapplication.R;

/**
 * Created by ZouWei on 2018/6/2.
 */
public class SettingFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }


    private Button mChineseBtn;
    private Button mEnglishBtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChineseBtn = view.findViewById(R.id.chinese);
        mEnglishBtn = view.findViewById(R.id.english);
        selectedChinese();
        mEnglishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEnglish();
                refreshSelf("zh");
            }
        });

        mChineseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedChinese();
                refreshSelf("en");
            }
        });
    }


    //refresh self
    public void refreshSelf(String Language){
        MyApplication.Language = Language;
        Intent intent=new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void selectedChinese() {
        mChineseBtn.setSelected(true);
        mEnglishBtn.setSelected(false);
    }

    private void selectedEnglish() {
        mChineseBtn.setSelected(false);
        mEnglishBtn.setSelected(true);
    }
}
