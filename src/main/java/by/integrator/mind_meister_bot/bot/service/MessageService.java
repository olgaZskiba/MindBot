package by.integrator.mind_meister_bot.bot.service;

import by.integrator.mind_meister_bot.bot.MindMeisterBot;
import by.integrator.mind_meister_bot.bot.bean.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageService {

    public final static String FIRSTNAME_PLACEHOLDER = "#{name}";
    public final static String EVENT_TRIAL_FIRSTNAME_PLACEHOLDER = "{name}";

    @Autowired
    private MindMeisterBot mindMeisterBot;

    public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        return mindMeisterBot.execute(sendMessage);
    }


    public SendMessage getMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update != null) {
            Message message = update.getMessage();
            sendMessage.setChatId(message.getChatId());
            if (message != null && message.hasText()) {
                String answer = message.getText();
                if (answer.equals("/start")) {
                    return sendMessage.setText("Привет ребята!");
                }
            }
        }
        return sendMessage.setText("Еще чего напиши");
    }

    public void setMessageIdAndDate(Profile profile, Message message) {
        profile.setBotLastMessageId(message.getMessageId());
        profile.setBotLastMessageDate(message.getDate());
        profile.setBotLastMessageEditable(message.getReplyToMessage() != null);
    }

    public Boolean hasFirstname(String message) {
        return message.contains(FIRSTNAME_PLACEHOLDER);
    }

    public Boolean hasEventTrialFirstname(String message) {
        return message.contains(EVENT_TRIAL_FIRSTNAME_PLACEHOLDER);
    }

}
