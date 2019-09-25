package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.lang.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText outcome;
    boolean flag;//判断文本框是否清空
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zero=(Button)findViewById(R.id.zero);
        Button one=(Button)findViewById(R.id.one);
        Button two=(Button)findViewById(R.id.two);
        Button three=(Button)findViewById(R.id.three);
        Button four=(Button)findViewById(R.id.four);
        Button five=(Button)findViewById(R.id.five);
        Button six=(Button)findViewById(R.id.six);
        Button seven=(Button)findViewById(R.id.seven);
        Button eight=(Button)findViewById(R.id.eight);
        Button nine=(Button)findViewById(R.id.nine);
        Button cls=(Button)findViewById(R.id.cls);
        Button div=(Button)findViewById(R.id.div);
        Button mul=(Button)findViewById(R.id.mul);
        Button del=(Button)findViewById(R.id.del);
        Button sub=(Button)findViewById(R.id.sub);
        Button add=(Button)findViewById(R.id.add);
        Button equal=(Button)findViewById(R.id.equal);
        Button point=(Button)findViewById(R.id.spot);
        outcome=(EditText)findViewById(R.id.outcome);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        cls.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        del.setOnClickListener(this);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        equal.setOnClickListener(this);
        point.setOnClickListener(this);
        flag=true;
    }

    @Override
    public void onClick(View view) {
        String str=outcome.getText().toString();
        switch (view.getId()){
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
            case R.id.spot:
                if(flag){
                    flag=false;
                    str="";
                    outcome.setText(str);
                }
                str=outcome.getText().toString();
                outcome.setText(str+((Button)view).getText());
                break;
            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div:
                if(flag){
                    flag=false;
                    str="";
                    outcome.setText(str);
                }
                str=outcome.getText().toString();
                char temp=str.charAt(str.length()-1);
                if(temp=='+'||temp=='-'||temp=='*'||temp=='/'||temp=='.')
                {
                    str=str.substring(0,str.length()-1);
                }
                    outcome.setText(str+((Button)view).getText());
                break;
            case R.id.cls:
                if(flag)
                    flag=false;
                str="";
                outcome.setText("");
                break;
            case R.id.del:
                if(flag){
                    flag=false;
                    str="";
                    outcome.setText(str);
                }
                else if(str!=null&&!str.equals("")){
                    str=outcome.getText().toString();
                    outcome.setText(str.substring(0,str.length()-1));
                }
                break;
            case R.id.equal:
                getOutcome();
        }
    }
    private void getOutcome(){
        String exp=outcome.getText().toString();//获取文本框内容
        if(exp==null||exp.equals("")){
            return;
        }

        if(flag){
            flag=false;
            return;
        }
        flag=true;
        calculate(exp);
    }
    protected int isp(char a)
    {
        switch(a){
            case '#':return 0;
            case '*':return 5;
            case '/':return 5;
            case '+':return 3;
            case '-':return 3;
        }
        return 0;
    }


    protected int icp(char a)
    {
        switch(a){
            case '#':return 0;
            case '*':return 4;
            case '/':return 4;
            case '+':return 2;
            case '-':return 2;
        }
        return 0;
    }

    protected void calculate(String equation) {
        Double temp1;
        Double temp2;
        Double result;
        char temp3;
        int i = 0;
        equation=equation+"#";
        List operator = new ArrayList();
        List<Double> operand = new ArrayList();
        operator.add('#');
        while (!operator.isEmpty()) {
            Double d;
            if (equation.charAt(i) >= '0' && equation.charAt(i) <= '9' || equation.charAt(i) == '.') {
                int j = 0;
                while (equation.charAt(i) >= '0' && equation.charAt(i) <= '9' || equation.charAt(i) == '.') {
                    j++;
                    i++;
                }
                d = Double.parseDouble(equation.substring(i - j,i));
                System.out.print(d);
                operand.add(d);
            } else {
                temp3 = (char)operator.get(operator.size() - 1);
                if (isp(temp3) < icp(equation.charAt(i))) {
                    operator.add(equation.charAt(i));
                    i++;
                } else if(isp(temp3) > icp(equation.charAt(i))){
                    operator.remove(operator.size() - 1);
                    temp1 = operand.get(operand.size() - 1);
                    operand.remove(operand.size() - 1);
                    temp2 = operand.get(operand.size() - 1);
                    operand.remove(operand.size() - 1);
                    System.out.print(temp1);
                    System.out.print(temp2);
                    switch (temp3) {
                        case '+':
                            result = temp1 + temp2;
                            operand.add(result);
                            break;
                        case '-':
                            result = temp2 - temp1;
                            operand.add(result);
                            break;
                        case '*':
                            result = temp1 * temp2;
                            operand.add(result);
                            break;
                        case '/':
                            if (temp1 == 0.0)
                            {result = 999999.0;operand.add(result);}
                            else {
                                result = temp2 / temp1;
                                operand.add(result);
                            }
                            break;
                    }
                }
                else
                    operator.remove(operator.size()-1);
            }
        }
        outcome.setText(String.valueOf(operand.get(0)));
        flag=false;
    }
}

