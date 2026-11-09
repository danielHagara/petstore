package data.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.startsWith;

public class TestHelper {

    private static final AtomicInteger NEXT_ORDER_ID = new AtomicInteger(
        ThreadLocalRandom.current().nextInt(1_000_000, Integer.MAX_VALUE - 1_000));
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private TestHelper() {}

    public static int generateRandomOrderId() {
        return NEXT_ORDER_ID.getAndIncrement();
    }

    public static int generatePetId() {
        return NEXT_ORDER_ID.getAndIncrement();
    }

    public static String getCurrentDateTimeString() {
        return OffsetDateTime.now(ZoneOffset.UTC).format(FORMATTER);
    }

    public static void assertHeaders(Response response) {
        assertThat(response.getHeader("Content-Type"), startsWith("application/json"));
        assertThat(response.getHeader("Access-Control-Allow-Headers"),
            equalToIgnoringCase("Content-Type, api_key, Authorization"));
        assertThat(response.getHeader("Access-Control-Allow-Methods"),
            equalToIgnoringCase("GET, POST, DELETE, PUT"));
        assertThat(response.getHeader("Access-Control-Allow-Origin"), equalToIgnoringCase("*"));
    }

}
