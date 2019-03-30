package com.example.wzh.dadabike.register_and_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzh.dadabike.MainActivity;
import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.customdialog.CustomDialog;
import com.example.wzh.dadabike.utils.Utils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.autonavi.ae.pos.LocManager.init;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ed_UserName,ed_Password;   //账号密码
    private Button btn_login,btn_register;        //登录注册
    private TextView tv_forgetPassword;         //忘记密码
    public long exitTime = 0;      //按下两次返回键的间隔时长

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_UserName = findViewById(R.id.et_username);
        ed_Password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        tv_forgetPassword = findViewById(R.id.tv_forget_password);

        //Bmob初始化
        Bmob.initialize(this,"787daaef44687d64da65e947ca61ffcd");

//        /**登陆一次之后下次直接跳过登录界面
//         * 每当你应用的用户注册成功或是第一次登录成功，都会在本地磁盘中有一个缓存的用户对象，
//         * 这样，你可以通过获取这个缓存的用户对象来进行登录：
//         */
        BmobUser bmobUser = new BmobUser();
        if (bmobUser != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }else {

        }

        //调用init方法
        init();
    }

    private void init(){
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_login:
                if (Utils.isFastClick()){
                    final BmobUser bmobUser = new BmobUser();
                    bmobUser.setUsername(ed_UserName.getText().toString());
                    bmobUser.setPassword(ed_Password.getText().toString());
                    bmobUser.login(new SaveListener<Object>() {
                        @Override
                        public void done(Object o, BmobException e) {
                            if(e==null){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            case R.id.btn_register:
                if (Utils.isFastClick()){
                    intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_forget_password:
                if (Utils.isFastClick()){
                    CustomDialog customDialog = new CustomDialog(LoginActivity.this);
                    customDialog.setTitle("温馨提示");
                    customDialog.setMassage("忘记密码就重新注册一个账号吧！");
                    customDialog.setQueding("好的", new CustomDialog.IquedingListener() {
                        @Override
                        public void queding(CustomDialog dialog) {

                        }
                    });
                    customDialog.setQuxiao("请点注册键", new CustomDialog.IquxiaoListener() {
                        @Override
                        public void quxiao(CustomDialog dialog) {

                        }
                    });
                }

                break;
        }
    }

    /**
     * //禁止使用返回键返回到上一页,但是按两下可以直接退出程序**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event); //继续执行父类其他点击事件
    }

    private void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
            System.exit(0);
        }

    }


}
