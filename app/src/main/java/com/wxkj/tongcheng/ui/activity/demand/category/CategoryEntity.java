package com.wxkj.tongcheng.ui.activity.demand.category;

import java.io.Serializable;
import java.util.List;

public class CategoryEntity implements Serializable {


    public List<TypeEntity> type;
    public List<AttributeEntity> attribute;
    public List<ValueEntity> value;

    /**
     * 类型
     */
    public static class TypeEntity implements Serializable {
        public int type_id;
        public String type_name;
        public String show_img;
        public int show_order;
    }

    /**
     * 扩展属性
     */
    public static class AttributeEntity implements Serializable {
        public int type_id;
        public int service_attribute_id;
        public String service_attribute_name;
        public int show_order;
        public int need_must;//1必须有值
    }

    /**
     * 扩展属相对应的值
     */
    public static class ValueEntity implements Serializable {
        public int service_attribute_id;
        public int attribute_data_id;
        public String attribute_data_name;
        public int show_order;
    }
}
