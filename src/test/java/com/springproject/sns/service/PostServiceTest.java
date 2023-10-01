package com.springproject.sns.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Test
    void 포스트_생성시_정상동작한다() {
//        TestInfoFixture.TestInfo fixture = TestInfoFixture.get();
//        when(userEntityRepository.findByUserName(fixture.getUserName())).thenReturn(Optional.of(UserEntityFixture.get(fixture.getUserName(), fixture.getPassword())));
//        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
//        Assertions.assertDoesNotThrow(() -> postService.create(fixture.getUserName(), fixture.getTitle(), fixture.getBody()));
    }

    @Test
    void 포스트생성시_유저가_존재하지_않으면_에러를_내뱉는다() {
//        TestInfoFixture.TestInfo fixture = TestInfoFixture.get();
//        when(userEntityRepository.findByUserName(fixture.getUserName())).thenReturn(Optional.empty());
//        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
//        SimpleSnsApplicationException exception = Assertions.assertThrows(SimpleSnsApplicationException.class, () -> postService.create(fixture.getUserName(), fixture.getTitle(), fixture.getBody()));
//
//        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}
