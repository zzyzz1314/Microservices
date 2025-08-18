package cn.zwh.ymcc;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
@FeignClient("service-search")
public interface SearchFeignAPI {
    @PostMapping("/es/save")
    JSONResult save(@RequestBody List<CourseDoc> courseDocs);
}
