package pdp.uz.jwtproject.service;

public interface SmsService {

    boolean sendMessage(String phone, String text);

}
