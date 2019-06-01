package com.framework.utils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BigDecimalTypeAdapter implements JsonSerializer<BigDecimal>,
		JsonDeserializer<BigDecimal> {

	public JsonElement serialize(BigDecimal src, Type arg1,
			JsonSerializationContext arg2) {
		String bigDecimalFormatAsString = "";
		if (src != null) {
			bigDecimalFormatAsString = src.setScale(2, RoundingMode.HALF_UP)
					.toString();
		}
		return new JsonPrimitive(bigDecimalFormatAsString);
	}

	public BigDecimal deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			return new BigDecimal(json.getAsString());
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

}