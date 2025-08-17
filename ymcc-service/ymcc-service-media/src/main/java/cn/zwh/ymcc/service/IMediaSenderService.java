package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.MediaFile;

public interface IMediaSenderService {
    boolean send(MediaFile mediaFile);
}
