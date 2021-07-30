package by.integrator.mind_meister_bot.bot.service;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ProfileMessageService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileLocalMessageService profileLocalMessageService;

    public String formatMessage(Profile profile, String message) {
        String firstName = "";

        if (messageService.hasFirstname(message)) {
            firstName = profile.getUserName() != null ? profile.getUserName() : "";
        }
        else if (messageService.hasEventTrialFirstname(message)){
            firstName = profile.getUserName() != null ? profile.getUserName() : "";
        }
        return message.replace(MessageService.FIRSTNAME_PLACEHOLDER, firstName).replace(MessageService.EVENT_TRIAL_FIRSTNAME_PLACEHOLDER, firstName);
    }


    @SneakyThrows
    public void sendStartEventRegistrationMessage(Profile profile) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(profile.getChatId());
        sendMessage.setText(profileLocalMessageService.getMessage("reply.event.start"));
        Message message = messageService.sendMessage(sendMessage);
        messageService.setMessageIdAndDate(profile, message);
    }

    @SneakyThrows
    public void sendEventEnterFirstnameMessage(Profile profile) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(profile.getChatId());
        sendMessage.setText(profileLocalMessageService.getMessage("reply.event.enterFirstname"));
        Message message = messageService.sendMessage(sendMessage);
        messageService.setMessageIdAndDate(profile, message);
    }

    @SneakyThrows
    public void sendEventNiceToMeetYouMessage(Profile profile) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(profile.getChatId());
        sendMessage.setText(formatMessage(profile, profileLocalMessageService.getMessage("reply.event.niceToMeetYou")));

        Message message = messageService.sendMessage(sendMessage);
        messageService.setMessageIdAndDate(profile, message);
    }
}
