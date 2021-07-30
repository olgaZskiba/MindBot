package by.integrator.mind_meister_bot.bot;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import by.integrator.mind_meister_bot.bot.bean.Role;
import by.integrator.mind_meister_bot.bot.handlers.ProfileCommandHandler;
import by.integrator.mind_meister_bot.bot.service.MessageService;
import by.integrator.mind_meister_bot.bot.service.ProfileService;
import by.integrator.mind_meister_bot.bot.service.SendBotMessageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Component
@PropertySource("application.properties")
public class MindMeisterBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private SendBotMessageService sendBotMessageService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileCommandHandler profileCommandHandler;

//    @Autowired
//    public MindMeisterBot(SendBotMessageService sendBotMessageService) {
//        this.sendBotMessageService = sendBotMessageService;
//    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        profileCommandHandler.handle(update);
//        Long profileId = getProfileId(update);
//        Profile profile = profileService.findByChatId(profileId);
//        if (profile != null) {
//            profileCommandHandler.handle(update);
//        } else {
//            profileRegistration(profileId, update);
//        }
    }



//        SendMessage sendMessage = messageService.getMessage(update);
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private Long getProfileId(Update update){
        if (update.hasMessage()){
            return update.getMessage().getFrom().getId().longValue();
        }else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().longValue();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom().getId().longValue();
        }
        return null;
    }

    private void profileRegistration(Long chatId, Update update){
        Profile profile = profileService.createProfile(chatId, update);
        profile.setRoles(Collections.singleton(Role.USER));
        profileService.save(profile);
    }
}
