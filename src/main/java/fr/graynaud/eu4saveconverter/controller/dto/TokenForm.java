package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.tuple.Pair;

public class TokenForm {

    @Schema(description = "The JWT token identifying the user", required = true)
    @JsonProperty(required = true)
    private String token;

    @Schema(description = "The timestamp at which the token expire", required = true)
    @JsonProperty(required = true)
    private Long expire;

    public TokenForm(Pair<Long, String> pair) {
        this.expire = pair.getLeft();
        this.token = pair.getRight();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
