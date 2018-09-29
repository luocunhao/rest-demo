package xlink.rest.demo.rest;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.restexpress.serialization.json.JacksonJsonProcessor;

/**
 */
public class JsonSerializationProcessor extends JacksonJsonProcessor {

	public JsonSerializationProcessor() {
	}

	@Override
	public <T> T deserialize(String string, Class<T> type) {
		return super.deserialize(string, type);
	}

	@Override
	public ByteBuffer serialize(Object object) {
		if (object == null) {
			return super.serialize(object);
		}

		if (object instanceof org.restexpress.domain.ErrorResult) {
			org.restexpress.domain.ErrorResult re = (org.restexpress.domain.ErrorResult) object;
			return ByteBuffer.wrap(re.getMessage().getBytes(Charset.forName("UTF-8")));
		}
		return ByteBuffer.wrap(object.toString().getBytes(Charset.forName("UTF-8")));

	}

}
