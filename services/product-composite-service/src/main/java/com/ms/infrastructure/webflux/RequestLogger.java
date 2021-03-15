package com.ms.infrastructure.webflux;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ms.constant.RequestHeaderConstant;
import com.ms.http.BaseResponse.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
public class RequestLogger {

    private static final String VERSION = "version";
    private static final String HTTP_STATUS = "httpStatus";
    private static final String URL = "url";
    private static final String METHOD = "method";
    private static final String RESPONSE = "response";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private RequestLogger() {
    }

    public static void error(ServerWebExchange exchange,
        HttpStatus httpStatus,
        Metadata errResp,
        Throwable ex,
        String version) {

        int status = httpStatus.value();
        JsonNode message = createMsg(exchange, status, errResp, version);
        var logMsg = String
            .format("Failed Request - %s - %s - %s", status, message.get(METHOD), message.get(URL));
        if (status < 400) {
            log.info(logMsg, ex);
        } else if (status < 500) {
            log.warn(logMsg, ex);
        } else {
            log.error(logMsg, ex);
        }
    }

    public static void info(ServerWebExchange exchange, long startTime, String version) {
        HttpStatus httpStatus = exchange.getResponse().getStatusCode();
        if (httpStatus == null) {
            log.warn("Can not get httpStatus code!");
            return;
        }
        int status = httpStatus.value();
        if (status < 300) {
            JsonNode msg = createMsg(exchange, status, version);
            log.info("Successful Request - {} - {} - {} - {} ms", status, msg.get(METHOD),
                msg.get(URL), (System.currentTimeMillis() - startTime) / 1000.0);
        }
    }

    private static ObjectNode createMsg(ServerWebExchange exchange,
        int httpStatus,
        String version) {
        ObjectNode objectMessage = objectMapper.createObjectNode()
            .put(VERSION, version)
            .put(HTTP_STATUS, httpStatus);

        String url = exchange.getRequest().getURI().getPath();
        if (exchange.getRequest().getURI().getQuery() != null) {
            url = url + "?" + exchange.getRequest().getURI().getQuery();
        }

        String requestId = exchange.getRequest().getHeaders()
            .getFirst(RequestHeaderConstant.X_REQUEST_ID);

        log.info("requestId: {}",requestId);

        var request = exchange.getRequest();
        var method = request.getMethod();
        var methodStr = (method != null) ? method.toString() : null;

        objectMessage.put(URL, url)
            .put(METHOD, methodStr)
            .put(RequestHeaderConstant.X_REQUEST_ID, requestId);

        return objectMessage;
    }

    private static JsonNode createMsg(ServerWebExchange exchange,
        int httpStatus,
        Metadata errResp,
        String version) {
        return createMsg(exchange, httpStatus, version)
            .set(RESPONSE, objectMapper.convertValue(errResp, JsonNode.class));
    }
}
