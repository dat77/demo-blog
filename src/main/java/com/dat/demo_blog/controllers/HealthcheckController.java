package com.dat.demo_blog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HealthcheckController {

  private final ObjectMapper objectMapper;

  public HealthcheckController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Operation(summary = "Check application health")
  @GetMapping(value = "/healthcheck")
  public ResponseEntity<?> healthCheck(
      @Parameter(description = "Format of the response", required = false, schema = @Schema(allowableValues = {"short", "full"}))
      @RequestParam(value = "format", required = false) String format,
      HttpServletRequest request){

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    if (format == null) {
      ObjectNode nullNode = objectMapper.createObjectNode();
      nullNode.put("message", "Status - OK");
      return ResponseEntity.status(HttpStatus.OK)
          .headers(headers)
          .body(nullNode);
    }

    switch (format) {
      case "short":
        ObjectNode shortNode = objectMapper.createObjectNode();
        shortNode.put("message", "Status - OK");
        shortNode.put("remote_ip", request.getRemoteAddr());
        return ResponseEntity.ok()
            .headers(headers)
            .body(shortNode);
      case "full":
        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        ObjectNode fullNode = objectMapper.createObjectNode();
        fullNode.put("message", "Status - OK");
        fullNode.put("remote_ip", request.getRemoteAddr());
        fullNode.put("server_time", currentTime);
        return ResponseEntity.ok()
            .headers(headers)
            .body(fullNode);
      default: return ResponseEntity.ok("OK");
    }

  }

}
