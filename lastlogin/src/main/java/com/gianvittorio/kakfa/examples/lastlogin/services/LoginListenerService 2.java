package com.gianvittorio.kakfa.examples.lastlogin.services;

import com.gianvittorio.kakfa.examples.lastlogin.bindings.UserListenerBinding;
import lombok.extern.slf4j.Slf4j;
import model.UserDetails;
import model.UserLogin;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Service
@Slf4j
@EnableBinding(UserListenerBinding.class)
public class LoginListenerService {

    @StreamListener
    public void process(@Input("user-master-channel") KTable<String, UserDetails> users,
                        @Input("user-login-channel") KTable<String, UserLogin> logins) {

        users.toStream().foreach((k, v) -> log.info("User Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC), v));

        logins.toStream().foreach((k, v) -> log.info("Login Key: {}, Last Login: {}, Value{}",
                k, Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC), v));

        logins.join(users, (l, u) -> {
            u.setLastLogin(l.getCreatedTime());
            return u;
        }).toStream().foreach((k, v) -> log.info("Updated Last Login Key: {}, Last Login: {}", k,
                Instant.ofEpochMilli(v.getLastLogin()).atOffset(ZoneOffset.UTC)));

    }
}
