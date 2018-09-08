package com.example.medicine.model.ro;

import com.example.medicine.model.expands.IFailureReason;
import com.example.medicine.model.expands.IResult;

public class AddUserOutput implements IResult, IFailureReason {

    private int resultStates;
    private int code;
    private String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getResultStates() {
        return resultStates;
    }

    @Override
    public void setResultStates(int resultStates) {
        this.resultStates = resultStates;
    }

    public enum FailureReasons {
        REASONS(1, "id不存在");

        private int code;
        private String message;

        FailureReasons() {
        }

        FailureReasons(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
