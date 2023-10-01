package com.springproject.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // user who occur alarm
    private Integer fromUserId; // 알람을 발생시킨 사람
    private Integer targetId;
}

