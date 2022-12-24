package com.rk.learnity.service;

import com.rk.learnity.dao.ContentDao;
import com.rk.learnity.dao.SubTopicDao;
import com.rk.learnity.dao.TopicDao;
import com.rk.learnity.dto.request.ContentDto;
import com.rk.learnity.entity.Content;
import com.rk.learnity.entity.SubTopic;
import com.rk.learnity.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    TopicDao topicDao;

    @Autowired
    SubTopicDao subTopicDao;

    @Autowired
    ContentDao contentDao;

    public Optional<List<ContentDto>> addContentByTopicId(List<ContentDto> contentDtos, String topicId) throws Exception {
        Optional<List<ContentDto>> optionalContentDtos = Optional.ofNullable(contentDtos);
        Optional<String> optionalTopicId = Optional.ofNullable(topicId);

        Optional<Topic> fromDbTopic = null;
        Optional<SubTopic> fromDbSubTopic = null;

        if (optionalTopicId.isPresent()) {
            Long topicIdLong = Long.parseLong(optionalTopicId.get());

            fromDbTopic = topicDao.findById(topicIdLong);
            fromDbSubTopic = subTopicDao.findById(topicIdLong);

        } else {
            throw new Exception("Pass topic id!!");
        }

        if (optionalContentDtos.isPresent() && (fromDbTopic.isPresent() || fromDbSubTopic.isPresent())) {
            contentDtos = optionalContentDtos.get();
            List<ContentDto> contentDtosRes = new ArrayList<>();

            for (ContentDto contentReq : contentDtos) {
                Content content = new Content();
                content.setType(contentReq.getType());
                content.setValue(contentReq.getValue());
                content.setOrder(contentReq.getOrder());
                if (fromDbTopic.isPresent()) {
                    content.setTopic(fromDbTopic.get());
                } else if (fromDbSubTopic.isPresent()) {
                    content.setSubTopic(fromDbSubTopic.get());
                }
                contentDtosRes.add(new ContentDto(contentDao.save(content)));
            }
            optionalContentDtos = Optional.ofNullable(contentDtosRes);
        } else {
            optionalContentDtos.get().clear();
            throw new Exception("Topic not found!!");
        }
        return optionalContentDtos;
    }

    public Optional<List<ContentDto>> getContentByTopicId(String topicId) throws Exception {
        Optional<List<ContentDto>> optionalContentDtos = Optional.ofNullable(null);
        Optional<String> optionalTopicId = Optional.ofNullable(topicId);

        Optional<Topic> fromDbTopic = null;
        Optional<SubTopic> fromDbSubTopic = null;

        if (optionalTopicId.isPresent()) {
            Long topicIdLong = Long.parseLong(optionalTopicId.get());

            fromDbTopic = topicDao.findById(topicIdLong);
            fromDbSubTopic = subTopicDao.findById(topicIdLong);


            if (fromDbTopic.isPresent() || fromDbSubTopic.isPresent()) {
                optionalContentDtos = Optional.ofNullable(contentDao.findByTopic_TopicIdOrderByOrderAsc(topicIdLong).stream().map(x -> new ContentDto(x)).collect(Collectors.toList()));
            } else {
                optionalContentDtos.get().clear();
                throw new Exception("Topic not found!!");
            }
        } else {
            throw new Exception("Pass topic id!!");
        }

        return optionalContentDtos;
    }
}
