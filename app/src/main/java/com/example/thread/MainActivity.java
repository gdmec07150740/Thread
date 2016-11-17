package com.example.thread;

/**
 * Created by 茹丽盈 on 2016/11/17.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.thread.R;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1= (TextView) findViewById(R.id.tv1);
        Date theLastDay=new Date(117,5,23);
        Date toDay=new Date();
        seconds= (int) ((theLastDay.getTime()-toDay.getTime())/1000);
    }

    public void anr(View v){
        for(int i=0;i<10000;i++){
            //不明白为什么会报错
            //  BitmapFactory.decodeResource(getResources(),R.drawable.android);
        }
    }

    public void threadclass(View v){
        class ThreadSample extends Thread{
            Random rm;
            public ThreadSample(String tname){
                super(tname);
                rm=new Random();
            }
            public void run(){
                for(int i=0;i<10;i++){
                    System.out.println(i+" "+getName());
                    try {
                        sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getName()+"完成");
            }
        }
        ThreadSample thread1=new ThreadSample("one");
        ThreadSample thread2=new ThreadSample("two");
        thread2.start();
        thread1.start();
    }

    public void runnableinterface(View v){
        class RunnableExample implements Runnable{
            Random rm;
            String name;
            public RunnableExample(String tname){
                this.name=tname;
                rm=new Random();
            }
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(i+" "+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"finish");
            }
        }
        Thread thread1=new Thread(new RunnableExample("one"));
        Thread thread2=new Thread(new RunnableExample("two"));
        thread1.start();
        thread2.start();
    }

    public void timertask(View v){
        class MyThread extends TimerTask{
            Random rm;
            String name;
            public MyThread(String tname){
                this.name=tname;
                rm=new Random();
            }
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(i+" "+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"finish");
            }
        }
        Timer timer1=new Timer();
        Timer timer2=new Timer();
        MyThread thread1=new MyThread("one");
        MyThread thread2=new MyThread("two");
        timer1.schedule(thread1,0);
        timer2.schedule(thread2,0);
    }

    public void handlermessage(View v){
        class MyTask extends TimerTask{
            int countdown;
            double achievement1=1,achievement2=2;

            public MyTask(int seconds){
                this.countdown=seconds;
            }
            @Override
            public void run() {
                Message mm=Message.obtain();
                mm.what=1;
                mm.arg1=countdown--;
                achievement1=achievement1*1.01;
                achievement2=achievement2*1.01;

                Bundle bundle=new Bundle();
                bundle.putString("attach","\nno money"+achievement1+"\nno talk"+achievement2);
                mm.setData(bundle);

            }
        }
        Timer timer=new Timer();
        timer.schedule(new MyTask(seconds),1,1000);
    }

    public void showmsg(String mm){
        tv1.setText(mm);
    }
    public void asynctask(View v){
        class LearnHard extends AsyncTask<Long,String,String>{
            private Context context;
            final int duration=10;
            int count=0;

            public LearnHard(Activity context){
                this.context=context;
            }
            @Override
            protected String doInBackground(Long... longs) {
                long num =longs[0].longValue();
                while(count<duration){
                    num--;
                    count++;
                    String status="还有"+num+"秒，还可以学习"+count+"秒";
                }
                return "这"+duration+"秒有收获，哈哈！" ;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                ((MainActivity)context).tv1.setText(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
                showmsg(s);
                super.onPostExecute(s);
            }


        }
        LearnHard lh=new LearnHard(this);
        lh.execute((long)seconds);
    }
}


