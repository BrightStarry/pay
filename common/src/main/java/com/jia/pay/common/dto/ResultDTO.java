package com.jia.pay.common.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.jia.pay.common.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * author:ZhengXing
 * datetime:2018-03-11 6:46
 * 标准返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultDTO<T> {

    /**
     * 基础视图，所有视图需要继承该视图，用于显示出code/message/data
     */
    public interface BaseView{}

    @JsonView(BaseView.class)
    private String code;

    @JsonView(BaseView.class)
    private String message;

    @JsonView(BaseView.class)
    private T data;

    public static ResultDTO<?> success() {
        return new ResultDTO<>(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage());
    }

    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<T>(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage()).setData(data);
    }

    public static ResultDTO<?> error(ErrorEnum errorEnum) {
        return new ResultDTO<>(errorEnum.getCode(), errorEnum.getMessage());
    }

    public static ResultDTO<?> error(String message) {
        return new ResultDTO<>(ErrorEnum.COMMON_ERROR.getCode(),message);
    }

    public static ResultDTO<?> error(String code,String message) {
        return new ResultDTO<>(code,message);
    }

    public ResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
