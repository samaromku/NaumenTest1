package com.example.andrey.naumentest.entities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Response {

    @Nullable
    private Object mAnswer;

    private ResultSet resultSet;

    public Response() {
//        this.resultSet = resultSet.ERROR;
    }

    @NonNull
    public ResultSet getRequestResult() {
        return this.resultSet;
    }

    public Response setRequestResult(ResultSet requestResult) {
        this.resultSet = requestResult;
        return this;
    }

    @Nullable
    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mAnswer;
    }

    public Response setAnswer(@Nullable Object answer) {
        mAnswer = answer;
        return this;
    }

    public void save(Context context) {
    }
}