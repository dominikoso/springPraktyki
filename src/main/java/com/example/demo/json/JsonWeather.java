
package com.example.demo.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cod",
    "message",
    "cnt",
    "list",
    "city"
})
public class JsonWeather {

    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private Double message;
    @JsonProperty("cnt")
    private Integer cnt;
    @JsonProperty("list")
    private java.util.List<GlobalWeatherObject> list = null;
    @JsonProperty("city")
    private City city;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cod")
    public String getCod() {
        return cod;
    }

    @JsonProperty("cod")
    public void setCod(String cod) {
        this.cod = cod;
    }

    @JsonProperty("message")
    public Double getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Double message) {
        this.message = message;
    }

    @JsonProperty("cnt")
    public Integer getCnt() {
        return cnt;
    }

    @JsonProperty("cnt")
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    @JsonProperty("list")
    public java.util.List<GlobalWeatherObject> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(java.util.List<GlobalWeatherObject> list) {
        this.list = list;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(City city) {
        this.city = city;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cod", cod).append("message", message).append("cnt", cnt).append("list", list).append("city", city).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).append(cnt).append(cod).append(additionalProperties).append(list).append(city).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JsonWeather) == false) {
            return false;
        }
        JsonWeather rhs = ((JsonWeather) other);
        return new EqualsBuilder().append(message, rhs.message).append(cnt, rhs.cnt).append(cod, rhs.cod).append(additionalProperties, rhs.additionalProperties).append(list, rhs.list).append(city, rhs.city).isEquals();
    }

}
