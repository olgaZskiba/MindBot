package by.integrator.mind_meister_bot.bot.states;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import by.integrator.mind_meister_bot.bot.interfaces.BotState;
import by.integrator.mind_meister_bot.bot.service.MessageService;
import by.integrator.mind_meister_bot.bot.service.ProfileMessageService;
import by.integrator.mind_meister_bot.bot.service.ProfileService;
import lombok.Setter;

public enum ProfileBotState implements BotState<ProfileBotState, ProfileBotContext> {

    StartEventRegistration(false){
        @Override
        public void enter(ProfileBotContext profileBotContext) {
            prfileMessageService.sendStartEventRegistrationMessage(profileBotContext.getProfile());
        }

        @Override
        public ProfileBotState nextState() {
            return EventRegistrationEnterFirstname;
        }
    },

    EventRegistrationEnterFirstname(true){
        ProfileBotState profileNextState = null;

        @Override
        public void enter(ProfileBotContext profileBotContext) {
        prfileMessageService.sendEventEnterFirstnameMessage(profileBotContext.getProfile());
        }

        @Override
        public void handleInput(ProfileBotContext profileBotContext) {
        String text = profileBotContext.getUpdate().getMessage().getText();
        Profile profile = profileBotContext.getProfile();
        profile.setUserName(text);
        profileService.save(profile);
        profileNextState = EventNiceToMeetYou; //EnterProfileAnswers;
        }
        @Override
        public ProfileBotState nextState() {
            return profileNextState;
        }
    },

    EventNiceToMeetYou(false){
        @Override
        public void enter(ProfileBotContext profileBotContext) {
    prfileMessageService.sendEventNiceToMeetYouMessage(profileBotContext.getProfile());

        }

        @Override
        public ProfileBotState nextState() {
            return null;
        }
    };


//    EnterProfileAnswers(true){
//
//    }


    public void handleInput(ProfileBotContext profileBotContext) {
    }

    public void handleCallbackQuery(ProfileBotContext profileBotContext) {
    }

    private final Boolean isInputNeeded;

    ProfileBotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }
    public Boolean getIsInputNeeded() {
        return isInputNeeded;
    }


    public static ProfileBotState getEventRegistrationState(){
        return StartEventRegistration;
    }

    @Setter
    private static ProfileMessageService prfileMessageService;
    @Setter
    private static ProfileService profileService;
    @Setter
    private static MessageService messageService;

    public abstract void enter(ProfileBotContext profileBotContext);

    public abstract ProfileBotState nextState();
}
