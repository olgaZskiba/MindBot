package by.integrator.mind_meister_bot.bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Locale;
@Service
public class ProfileLocalMessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public ProfileLocalMessageService(@Value("${localeTagProfile}") String localeTag, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeTag);
        this.messageSource = messageSource;
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}
