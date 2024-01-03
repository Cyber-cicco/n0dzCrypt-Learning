package fr.diginamic.digilearning.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void isDigits() {
        assertTrue(StringUtils.isDigits("123345"));
        assertTrue(StringUtils.isDigits("1"));
        assertFalse(StringUtils.isDigits(""));
        assertFalse(StringUtils.isDigits("12 3"));
    }

    @Test
    void getIndexOfCounterIfPresent() {
        assertEquals(4, StringUtils.getIndexOfCounterIfPresent("test_1").get());
        assertEquals(5, StringUtils.getIndexOfCounterIfPresent("test__1").get());
        assertEquals(5, StringUtils.getIndexOfCounterIfPresent("test__11111111").get());
        assertEquals(Optional.empty(), StringUtils.getIndexOfCounterIfPresent("test-1"));
    }

}