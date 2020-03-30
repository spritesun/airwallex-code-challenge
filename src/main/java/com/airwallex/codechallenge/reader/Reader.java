package com.airwallex.codechallenge.reader;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

import com.airwallex.codechallenge.message.RateMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {

    private ObjectMapper mapper = jacksonObjectMapper().registerModule(new JavaTimeModule());

    public Stream<RateMessage> read(String filename) throws IOException {
        return read(Files.lines(Paths.get(filename)));
    }

    private Stream<RateMessage> read(Stream<String> lines) {
        return lines.map(this::parse);
    }

    @Nullable
    private RateMessage parse(String it) {
        try {
            return mapper.readValue(it, RateMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
