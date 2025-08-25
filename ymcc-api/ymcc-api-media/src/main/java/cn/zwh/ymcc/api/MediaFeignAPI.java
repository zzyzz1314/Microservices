package cn.zwh.ymcc.api;

import cn.zwh.ymcc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-media")
public interface MediaFeignAPI {
    @GetMapping("/mediaFile/selectMediaFileByCourseId/{courseId}")
    JSONResult selectMediaFileByCourseId(@PathVariable("courseId") Long courseId);
}
