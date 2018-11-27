package com.system.service;

import com.system.bean.Dictionary;
import com.system.service.hystrix.DictionaryServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author zzy
 * @Date 2018/8/17 下午1:37
 */
@FeignClient(name = "systemAdmin", fallback = DictionaryServiceHystrix.class)
public interface DictionaryService {

    @GetMapping(value = "/dic/page/{page}")
    String getPage(@PathVariable("page") Integer page, @RequestParam("pageSize") Integer pageSize);

    @PostMapping(value = "/dic")
    String add(Dictionary dictionary);

    @PutMapping(value = "/dic")
    String update(Dictionary dictionary);

    @DeleteMapping(value = "/dic")
    String delete(@RequestParam("ids") Integer [] ids);

    @GetMapping("/dic/{id}")
    String getDictionary(@PathVariable("id") Integer id);

    @GetMapping("/dic/check/code")
    String checkCode(@RequestParam("code") String code,@RequestParam("id") Integer id);

    @GetMapping("/dic/check/value")
    String checkValue(@RequestParam("value") String value,@RequestParam("parentId") Integer parentId,@RequestParam("id") Integer id);

    @GetMapping(value = "/dic/name")
    public String getName(@RequestParam("code") String code,@RequestParam("value") String value);

    @RequestMapping(value = "/dic/code", method = RequestMethod.GET)
    public String getDicListByCode(@RequestParam("code") String code);
}
