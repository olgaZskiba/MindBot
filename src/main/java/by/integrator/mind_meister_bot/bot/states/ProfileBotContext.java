package by.integrator.mind_meister_bot.bot.states;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import by.integrator.mind_meister_bot.bot.handlers.ProfileCommandHandler;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class ProfileBotContext {
    private final ProfileCommandHandler profileCommandHandler;
    private final Profile profile;
    private final Update update;


    public ProfileBotContext(ProfileCommandHandler profileCommandHandler, Profile profile, Update update) {
        this.profileCommandHandler = profileCommandHandler;
        this.profile = profile;
        this.update = update;
    }

    public static ProfileBotContext of(ProfileCommandHandler profileCommandHandler, Profile profile, Update update){
        return new ProfileBotContext(profileCommandHandler, profile, update);
    }
}
