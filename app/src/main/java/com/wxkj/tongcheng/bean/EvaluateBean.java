package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 评论数据
 */
public class EvaluateBean implements Serializable {

    /**
     * discuss_id : 2
     * discuss_content : test1
     * user_cname : 用户中文名
     * head_portrait : http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png
     * time_setup : 1535731200
     * goods_name : ""
     * pics : [{"discuss_id":2,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png","discusspic_id":6518},{"discuss_id":2,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png","discusspic_id":7437}]
     */

    private int discuss_id;
    private String discuss_content;
    private String user_cname;
    private String goods_name;
    private String head_portrait;
    private int time_setup;
    private List<PicsBean> pics;

    public int getDiscuss_id() {
        return discuss_id;
    }

    public void setDiscuss_id(int discuss_id) {
        this.discuss_id = discuss_id;
    }

    public String getDiscuss_content() {
        return discuss_content;
    }

    public void setDiscuss_content(String discuss_content) {
        this.discuss_content = discuss_content;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUser_cname() {
        return user_cname;
    }

    public void setUser_cname(String user_cname) {
        this.user_cname = user_cname;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public int getTime_setup() {
        return time_setup;
    }

    public void setTime_setup(int time_setup) {
        this.time_setup = time_setup;
    }

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }

    public static class PicsBean {
        /**
         * discuss_id : 2
         * pic_path : http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png
         * discusspic_id : 6518
         */

        private int discuss_id;
        private String pic_path;
        private int discusspic_id;

        public int getDiscuss_id() {
            return discuss_id;
        }

        public void setDiscuss_id(int discuss_id) {
            this.discuss_id = discuss_id;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public int getDiscusspic_id() {
            return discusspic_id;
        }

        public void setDiscusspic_id(int discusspic_id) {
            this.discusspic_id = discusspic_id;
        }
    }
}
