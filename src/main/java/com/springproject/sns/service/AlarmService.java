package com.springproject.sns.service;

import com.springproject.sns.exception.ErrorCode;
import com.springproject.sns.exception.SimpleSnsApplicationException;
import com.springproject.sns.model.AlarmArgs;
import com.springproject.sns.model.AlarmNoti;
import com.springproject.sns.model.AlarmType;
import com.springproject.sns.model.entity.AlarmEntity;
import com.springproject.sns.model.entity.UserEntity;
import com.springproject.sns.repository.AlarmEntityRepository;
import com.springproject.sns.repository.EmitterRepository;
import com.springproject.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final static String ALARM_NAME = "alarm";

    private final AlarmEntityRepository alarmEntityRepository;
    private final EmitterRepository emitterRepository;
    private final UserEntityRepository userEntityRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public void send(AlarmType type, AlarmArgs args, Integer receiverId) {
        UserEntity userEntity = userEntityRepository.findById(receiverId).orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND));
        AlarmEntity entity = AlarmEntity.of(type, args, userEntity);
        alarmEntityRepository.save(entity);
        emitterRepository.get(receiverId).ifPresentOrElse(it -> {
                    try {
                        it.send(SseEmitter.event()
                                .id(entity.getId().toString())
                                .name(ALARM_NAME)
                                .data(new AlarmNoti()));
                    } catch (IOException exception) {
                        emitterRepository.delete(receiverId); // 에러가 난 경우 userId를 더이상 캐시에 들고 있을 필요가 없어지므로 delete
                        throw new SimpleSnsApplicationException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
                    }
                },
                () -> log.info("No emitter founded")
        );
    }


    public SseEmitter connectNotification(Integer userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, emitter);
        emitter.onCompletion(() -> emitterRepository.delete(userId));
        emitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            log.info("send");
            emitter.send(SseEmitter.event()
                    .id("id")
                    .name(ALARM_NAME)
                    .data("connect completed"));
        } catch (IOException exception) {
            throw new SimpleSnsApplicationException(ErrorCode.NOTIFICATION_CONNECT_ERROR);
        }
        return emitter;
    }

}

