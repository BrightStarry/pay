package com.jia.pay.web;

import com.jia.pay.business.SystemService;
import com.jia.pay.common.dto.ResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  系统
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;


    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 清空缓存
     */
    @GetMapping("/delete/cache/{cacheName}")
    public ResultDTO deleteCache(@PathVariable String cacheName) {
        systemService.deleteCache(cacheName);
        return ResultDTO.success();
    }

    /**
     * 清空单个key
     * 该key不携带 cacheName
     */
    @GetMapping("/delete/cache/{cacheName}/{key}")
    public ResultDTO deleteCache(@PathVariable String cacheName, @PathVariable String key) {
        systemService.deleteCacheKey(cacheName,key);
        return ResultDTO.success();
    }



}
