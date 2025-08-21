package cn.zwh.ymcc;

import cn.zwh.ymcc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-course")
public interface CourseFeignAPI {
    @GetMapping("/course/info/{courseId}")
    JSONResult info(@PathVariable("courseId") String courseIds);
}
