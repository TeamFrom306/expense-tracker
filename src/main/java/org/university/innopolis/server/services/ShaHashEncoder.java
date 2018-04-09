package org.university.innopolis.server.services;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ShaHashEncoder implements EncoderService {
    @Override
    public String getHash(String s) {
        return Hashing.sha256()
                .hashString(s, StandardCharsets.UTF_8)
                .toString();
    }
}
