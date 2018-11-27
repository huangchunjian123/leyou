package com.system.common.util;

/**
 * 字段信息表
 */
class FieldMeta {
    private String fieldName;    //字段名
    private String fieldDataType;   //字段类型
    private int fieldLength;    //字段长度
    private String fieldComment;    //字段备注

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDataType() {
        if(fieldDataType.equalsIgnoreCase("bit")){
            return "java.lang.Boolean";
        }else if(fieldDataType.equalsIgnoreCase("tinyint")){
            return "java.lang.Byte";
        }else if(fieldDataType.equalsIgnoreCase("smallint")){
            return "java.lang.Short";
        }else if(fieldDataType.equalsIgnoreCase("int")){
            return "java.lang.Integer";
        }else if(fieldDataType.equalsIgnoreCase("bigint") || fieldDataType.equalsIgnoreCase("TIMESTAMP")){
            return "java.lang.Long";
        }else if(fieldDataType.equalsIgnoreCase("float")) {
            return "java.lang.Float";
        }else if(fieldDataType.equalsIgnoreCase("decimal")){
            return "java.math.BigDecimal";
        }else if(fieldDataType.equalsIgnoreCase("numeric")
                || fieldDataType.equalsIgnoreCase("real") || fieldDataType.equalsIgnoreCase("money")
                || fieldDataType.equalsIgnoreCase("smallmoney")){
            return "java.lang.Double";
        }else if(fieldDataType.equalsIgnoreCase("varchar") || fieldDataType.equalsIgnoreCase("char")
                || fieldDataType.equalsIgnoreCase("nvarchar") || fieldDataType.equalsIgnoreCase("nchar")
                || fieldDataType.equalsIgnoreCase("text") || fieldDataType.equalsIgnoreCase("LONGTEXT")){
            return "java.lang.String";
        }else if(fieldDataType.equalsIgnoreCase("datetime")||fieldDataType.equalsIgnoreCase("year")
                || fieldDataType.equalsIgnoreCase("date")){
            return "java.util.Date";
        }
        return fieldDataType;
    }
    public void setFieldDataType(String fieldDataType) {
        if(fieldDataType.equalsIgnoreCase("bit")){
            this.fieldDataType = "java.lang.Boolean";
        }else if(fieldDataType.equalsIgnoreCase("tinyint")){
            this.fieldDataType = "java.lang.Byte";
        }else if(fieldDataType.equalsIgnoreCase("smallint")){
            this.fieldDataType = "java.lang.Short";
        }else if(fieldDataType.equalsIgnoreCase("int")){
            this.fieldDataType = "java.lang.Integer";
        }else if(fieldDataType.equalsIgnoreCase("bigint")){
            this.fieldDataType = "java.lang.Long";
        }else if(fieldDataType.equalsIgnoreCase("float")){
            this.fieldDataType = "java.lang.Float";
        }else if(fieldDataType.equalsIgnoreCase("decimal")){
            this.fieldDataType = "java.math.BigDecima";
        }else if(fieldDataType.equalsIgnoreCase("numeric")
                || fieldDataType.equalsIgnoreCase("real") || fieldDataType.equalsIgnoreCase("money")
                || fieldDataType.equalsIgnoreCase("smallmoney")){
            this.fieldDataType = "java.lang.Double";
        }else if(fieldDataType.equalsIgnoreCase("varchar") || fieldDataType.equalsIgnoreCase("char")
                || fieldDataType.equalsIgnoreCase("nvarchar") || fieldDataType.equalsIgnoreCase("nchar")
                || fieldDataType.equalsIgnoreCase("text") || fieldDataType.equalsIgnoreCase("LONGTEXT")){
            this.fieldDataType = "java.lang.String";
        }else if(fieldDataType.equalsIgnoreCase("datetime") ||fieldDataType.equalsIgnoreCase("year")
                || fieldDataType.equalsIgnoreCase("date")){
            this.fieldDataType = "java.util.Date";
        }else{
            this.fieldDataType = fieldDataType;
        }

    }

    public int getFieldLength() {
        return fieldLength;
    }
    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldComment() {
        return fieldComment;
    }
    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

}
