package io.instanto.compat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
public record Payload(
    String name,
    int count,
    List<String> labels,
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC") Date date) {}
