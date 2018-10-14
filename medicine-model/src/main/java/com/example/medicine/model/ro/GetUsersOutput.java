package com.example.medicine.model.ro;

import com.example.medicine.model.expands.IContent;
import com.example.medicine.model.expands.IResult;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;
import java.util.List;

public class GetUsersOutput implements IResult, IContent<List<GetUsersOutput.User>>, Serializable {

    private int resultStates;
    private List<User> content;

    @Override
    public int getResultStates() {
        return resultStates;
    }

    @Override
    public void setResultStates(int resultStates) {
        this.resultStates = resultStates;
    }

    @Override
    public List<User> getContent() {
        return content;
    }

    @Override
    public void setContent(List<User> content) {
        this.content = content;
    }


    public static class User implements Serializable {

        @ApiModelProperty("用户名")
        private String name;
        @ApiModelProperty("年龄")
        private Integer age;
        private String sex;
        private List<Test> list;

        public List<Test> getList() {
            return list;
        }

        public void setList(List<Test> list) {
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static class Test {
        @ApiModelProperty("用户名")
        private String name;
        @ApiModelProperty("年龄")
        private Integer age;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
