package data.utils;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.restassured.http.Header;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestHelper {

    private static final int ORDER_ID_BOUNDARY = 1_000_000;
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final Map<String, String> expected = Map.of(
        "content-type", "application/json",
        "access-control-allow-headers", "Content-Type, api_key, Authorization",
        "access-control-allow-methods", "GET, POST, DELETE, PUT",
        "access-control-allow-origin", "*"
);

    private TestHelper() {}

    public static int generateRandomOrderId() {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(ORDER_ID_BOUNDARY);
    }

    public static String getCurrentDateTimeString() {
        return OffsetDateTime.now(ZoneOffset.UTC).format(FORMATTER);
    }

    public static void assertHeaders(Response response) {
        List<Header> observedHeaders = response.getHeaders().asList();

        List<Header> expectedHeaders = expected
        .entrySet()
        .stream()
        .map(e -> new Header(e.getKey(), e.getValue()))
        .collect(Collectors.toList());

        assertThat(expectedHeaders, everyItem(is(in(observedHeaders))));
    }

}
