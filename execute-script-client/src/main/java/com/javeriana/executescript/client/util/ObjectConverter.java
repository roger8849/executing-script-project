package com.javeriana.executescript.client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javeriana.executescript.client.dto.Address;
import com.javeriana.executescript.client.dto.Message;

public class ObjectConverter {

  public static final Logger LOG = LoggerFactory.getLogger(ObjectConverter.class);

  // public static byte[] fromMessageToByteData(MulticastMessage message) throws IOException {
  // // LOG.debug("Converting message to byte data: {}", message);
  // // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  // // ObjectOutputStream os = new ObjectOutputStream(outputStream);
  // // os.writeObject(message);
  // // byte[] byteArray = outputStream.toByteArray();
  // // LOG.debug("Message successfuly converted. {}", byteArray);
  // // return byteArray;
  // return SerializationUtils.serialize(message);
  // }

  public static byte[] fromMessageToByteData(Message message) throws IOException {
    String jsonObject = fromObjectToJsonString(message);
    byte[] data = jsonObject.getBytes();
    return data;
  }

  // public static MulticastMessage fromByteDataToMessage(byte[] data)
  // throws IOException, ClassNotFoundException {
  // // LOG.debug("Converting byte to multicast Message: {}", data);
  // // ByteArrayInputStream in = new ByteArrayInputStream(data);
  // // ObjectInputStream is = new ObjectInputStream(in);
  // // MulticastMessage multicastObject = (MulticastMessage) is.readObject();
  // // LOG.debug("Multicast object transformed: {}", multicastObject);
  // return SerializationUtils.deserialize(data);
  // }
  public static Message fromByteDataToMessage(byte[] data)
      throws IOException, ClassNotFoundException {
    String jsonString = new String(data);
    jsonString = jsonString.trim();
    return fromJsonStringToMulticastMessage(jsonString);
  }

  public static String fromObjectToJsonString(Object object)
      throws JsonGenerationException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  public static Message fromJsonStringToMulticastMessage(String jsonObject) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(jsonObject, Message.class);
  }

  public static List<Address> fromPropertiesAddressToAddressList(List<String> urls) {
    List<Address> addresses = new ArrayList<>();
    for (String url : urls) {
      String[] urlPair = url.split(":");
      addresses.add(new Address(urlPair[0], Integer.parseInt(urlPair[1])));
    }
    return addresses;
  }

  private ObjectConverter() {}

}
