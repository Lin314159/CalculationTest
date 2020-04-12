package com.example.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;
    final static String KEY_HEIGH_SCORE = "key_heigh_score";
    final static String KEY_LEFT_NUMBER = "key_left_number";
    final static String KEY_RIGHT_NUMBER = "key_right_number";
    final static String KEY_OPERATOR = "key_operator";
    final static String KEY_ANSWER = "key_answer";
    final static String KEY_CURRENT_SCORE = "key_current_score";
    final static String KEY_SHP_NAME = "key_shp_name";
    boolean win_flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HEIGH_SCORE)) {
            SharedPreferences preferences = getApplication().getSharedPreferences(KEY_SHP_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HEIGH_SCORE, preferences.getInt(KEY_HEIGH_SCORE, 0));
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_CURRENT_SCORE, 0);
            handle.set(KEY_ANSWER, 0);
        }
        this.handle = handle;

    }

    void generator() {
        int LEVEL = 100;
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;
        y = random.nextInt(LEVEL) + 1;
        boolean b = random.nextBoolean();
        if (b) {
            getOperator().setValue("+");
            if (x > y) {
                getAnswer().setValue(x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x - y);
            } else {
                getAnswer().setValue(y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(x - y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y);
            } else {
                getAnswer().setValue(y - x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x);
            }
        }

    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences preferences = getApplication().getSharedPreferences(KEY_SHP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_HEIGH_SCORE, getHeighScore().getValue());
        editor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    void answerRight() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHeighScore().getValue()) {
            getHeighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }


    public MutableLiveData<Integer> getLeftNum() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNum() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<Integer> getHeighScore() {
        return handle.getLiveData(KEY_HEIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(KEY_ANSWER);
    }

}
