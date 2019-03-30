package com.example.wzh.dadabike.register_and_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wzh.dadabike.R;
import com.example.wzh.dadabike.utils.Utils;

import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_userName,ed_password,ed_number,ed_email;     //用户账号，密码,手机号码，邮箱
    private Button btn_register;            //注册按钮
    private String userName,password,number,email;

    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,9}$";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_userName = findViewById(R.id.signup_username);
        ed_password = findViewById(R.id.signup_password);
        ed_number = findViewById(R.id.signup_number);
        ed_email = findViewById(R.id.register_email);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);

        //Bmob初始化
        Bmob.initialize(this,"787daaef44687d64da65e947ca61ffcd");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register){
            if (Utils.isFastClick()){
                BmobUser user = new BmobUser();
                userName = ed_userName.getText().toString();
                password = ed_password.getText().toString();
                number = ed_number.getText().toString();
                email = ed_email.getText().toString();

                if (isUserName(userName) && isPassword(userName) && isMobile(number) && isEmail(email)){
                    user.setUsername(userName);
                    user.setPassword(password);
                    user.setMobilePhoneNumber(number);
                    user.setEmail(email);
                    user.signUp(new SaveListener<Table_UserMessage>() {
                        @Override
                        public void done(Table_UserMessage table_userMassage, BmobException e) {
                            if (e == null){
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegisterActivity.this,"注册失败"+e,Toast.LENGTH_SHORT).show();
                                Log.d("register", e+"");
                            }
                        }
                    });
                }else {
                    if (!isUserName(userName)){
                        ed_userName.setError("账号由数字和字母组成，开头必须为字母，且不超过10位！");
                    }
                    else if (!isPassword(password)){
                        ed_password.setError("密码由数字和字母组成，长度不少6位且不超过16位！");
                    }
                    else if (!isMobile(number)){
                        ed_number.setError("手机号码格式不对！");
                    }
                    else if (!isEmail(email)){
                        ed_email.setError("邮箱格式不对！");
                    }
                }
            }

        }
    }

    /**
     * 校验用户名
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
