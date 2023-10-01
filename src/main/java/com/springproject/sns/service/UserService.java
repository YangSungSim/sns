package com.springproject.sns.service;

import com.springproject.sns.exception.ErrorCode;
import com.springproject.sns.exception.SimpleSnsApplicationException;
import com.springproject.sns.model.Alarm;
import com.springproject.sns.model.User;
import com.springproject.sns.model.entity.UserEntity;
import com.springproject.sns.repository.AlarmEntityRepository;
import com.springproject.sns.repository.UserCacheRepository;
import com.springproject.sns.repository.UserEntityRepository;
import com.springproject.sns.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCacheRepository redisRepository;
    private final AlarmEntityRepository alarmEntityRepository;

    @Transactional
    public User join(String userName, String password) {
        // check the userId not exist
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SimpleSnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userName));
        });

        // if the userId doesn't exist then register
        UserEntity savedUser = userEntityRepository.save(UserEntity.of(userName, bCryptPasswordEncoder.encode(password)));
        return User.fromEntity(savedUser);
    }

    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return redisRepository.getUser(userName).orElseGet(
                () -> userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                        () -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
                ));
    }

    public String login(String userName, String password) {
        // 가입된 유저 정보 불러오기
        User savedUser = loadUserByUsername(userName);
        redisRepository.setUser(savedUser);
        // 비밀번호 체크
        if (!bCryptPasswordEncoder.matches(password, savedUser.getPassword())) {
            throw new SimpleSnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
    }

    @Transactional
    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
    }

}
