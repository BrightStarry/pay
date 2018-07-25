package com.jia.pay.web.system;

import com.jia.pay.common.dto.ResultDTO;
import com.jia.pay.common.enums.ErrorEnum;
import com.jia.pay.config.Constant;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * author:ZhengXing
 * datetime:2018-03-11 6:39
 * 异常控制类
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * rest请求异常返回
     *
     * 此处路径配置为  /error/ 是因为basicErrorController中已有 /error 路径
     * (可自己实现{@link ErrorController 接口}，覆盖{@link org.springframework.boot.autoconfigure.web.BasicErrorController})
     */
    @ResponseBody
    @RequestMapping(value = "/")
    @SneakyThrows
    public ResultDTO<?> common(HttpServletRequest request) {
        return ResultDTO.error(
                ServletRequestUtils.getStringParameter(request, Constant.ERROR_CODE_FIELD,ErrorEnum.UNKNOWN_ERROR.getCode()),
                ServletRequestUtils.getStringParameter(request,Constant.ERROR_MESSAGE_FIELD,ErrorEnum.UNKNOWN_ERROR.getMessage())
        );
    }

    /**
     * 404 rest
     */
    @ResponseBody
    @RequestMapping(value = "/404")
    public ResultDTO<?> error404 () {
        return  ResultDTO.error(ErrorEnum.NOT_FOUND_ERROR);
    }

    /**
     * html 请求异常返回
     */
    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String commonHtml(HttpServletRequest request, Model model) {
        model.addAttribute(Constant.ERROR_CODE_FIELD, request.getAttribute(Constant.ERROR_CODE_FIELD));
        model.addAttribute(Constant.ERROR_MESSAGE_FIELD, request.getAttribute(Constant.ERROR_MESSAGE_FIELD));
        return Constant.ERROR_VIEW_PATH;
    }

    /**
     * 404-页面
     */
    @RequestMapping(value = "/404",produces = MediaType.TEXT_HTML_VALUE)
    public String error404Html(Model model) {
        model.addAttribute(Constant.ERROR_CODE_FIELD, ErrorEnum.NOT_FOUND_ERROR.getCode());
        model.addAttribute(Constant.ERROR_MESSAGE_FIELD, ErrorEnum.NOT_FOUND_ERROR.getMessage());
        return Constant.ERROR_VIEW_PATH;
    }
}
