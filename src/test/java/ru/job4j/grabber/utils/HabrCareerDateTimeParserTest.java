package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {

    DateTimeParser habrCareerDateTimeParser = new HabrCareerDateTimeParser();

    @Test
    void whenParseDateIsNotOffset() {
        String date = "2019-08-08T06:00:00";
        LocalDateTime expected = LocalDateTime.of(2019, 8, 8, 6, 0, 0);
        LocalDateTime result = habrCareerDateTimeParser.parse(date);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenParseDateIsOffset() {
        String date = "2022-05-27T10:30:00+01:00";
        LocalDateTime expected = LocalDateTime.of(2022, 5, 27, 10, 30, 0);
        LocalDateTime result = habrCareerDateTimeParser.parse(date);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenParseAnIncorrectDate() {
        String date = "2024-09-01T10:30:69";
        assertThatThrownBy(() -> habrCareerDateTimeParser.parse(date))
                .isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("Invalid value for SecondOfMinute");
    }

    @Test
    void whenParseWithDifferentSeparators() {
        String date = "2025-02-21 14:30:00";
        assertThatThrownBy(() -> habrCareerDateTimeParser.parse(date))
                .isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("Text '2025-02-21 14:30:00' could not be parsed");
    }

}