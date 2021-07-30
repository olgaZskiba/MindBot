package by.integrator.mind_meister_bot.bot.states;

import by.integrator.mind_meister_bot.bot.interfaces.BotStateInjector;
import by.integrator.mind_meister_bot.bot.service.MessageService;
import by.integrator.mind_meister_bot.bot.service.ProfileMessageService;
import by.integrator.mind_meister_bot.bot.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProfileBotStateInjector implements BotStateInjector<ProfileBotState, ProfileBotContext> {
    @Autowired
    private ProfileMessageService profileMessageService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MessageService messageService;

    @Override
    @PostConstruct
    public void inject() {
    ProfileBotState.setPrfileMessageService(profileMessageService);
    ProfileBotState.setProfileService(profileService);
    ProfileBotState.setMessageService(messageService);
    }
}
