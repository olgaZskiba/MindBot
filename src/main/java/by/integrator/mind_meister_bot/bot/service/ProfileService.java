package by.integrator.mind_meister_bot.bot.service;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import by.integrator.mind_meister_bot.bot.repository.ProfileRepository;
import by.integrator.mind_meister_bot.bot.states.ProfileBotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    @Transactional
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    @Transactional
    public Profile findByChatId(Long chatId) {
        return profileRepository.findByChatId(chatId);
    }

    public Profile createProfile(Long chatId, Update update) {
        Profile profile = Profile.builder()
                .chatId(chatId)
                .userName(update.getMessage().getFrom().getUserName())
                .botLastMessageDate(0)
                .botLastMessageId(0)
                .botLastMessageEditable(false)
                .build();

        profile.setProfileBotState(ProfileBotState.getEventRegistrationState());

        return profile;
    }
}
