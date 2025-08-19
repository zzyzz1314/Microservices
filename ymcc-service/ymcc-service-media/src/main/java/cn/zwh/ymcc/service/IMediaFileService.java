package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.MediaFile;
import cn.zwh.ymcc.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-16
 */
public interface IMediaFileService extends IService<MediaFile> {

    /**
     * 文件上传之前的注册功能
     */
    JSONResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    /**
     * 校验文件块是否已经存在了
     */
    JSONResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize);

    /**
     * 上传文件块
     */
    JSONResult uploadchunk(MultipartFile file, String fileMd5, Integer chunk);

    /**
     * 合并文件快
     */
    JSONResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt,
                           Long courseChapterId, Long courseId, Integer number, String name, String courseName, String chapterName);

    //处理文件
    JSONResult handleFile2m3u8(MediaFile mediaFile);

    //根据课程id查询课程媒资
    List<MediaFile> selectMediaFileByCourseId(Long courseId);
}
