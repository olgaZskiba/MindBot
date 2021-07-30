package by.integrator.mind_meister_bot.bot.handlers;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import by.integrator.mind_meister_bot.bot.service.ProfileService;
import by.integrator.mind_meister_bot.bot.states.ProfileBotContext;
import by.integrator.mind_meister_bot.bot.states.ProfileBotState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@RequiredArgsConstructor
public class ProfileCommandHandler {
    @Autowired
    private ProfileService profileService;


    public void handle(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                processInputMessage(update);
            }
        }
//        }else if (update.hasCallbackQuery()) {
//            processCallbackQuery(update);
//        }
    }

    private void processInputMessage(Update update) throws TelegramApiException {
        final Long chatId = update.getMessage().getChatId();
        ProfileBotContext botContext;
        ProfileBotState botState;

        Profile profile = profileService.findByChatId(chatId);

        if (profile == null) {
            profile = profileService.createProfile(chatId, update);

             botContext = ProfileBotContext.of(this, profile, update);
             botState = profile.getProfileBotState();

            botState.enter(botContext);

            while (!botState.getIsInputNeeded()) {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            }
        } else {
             botContext = ProfileBotContext.of(this, profile, update);
             botState = profile.getProfileBotState();
            profile.setProfileBotState(ProfileBotState.StartEventRegistration);
            profileService.save(profile);
            //            logger.info("Update received from user: " + chatId + ", in state: " + botState + ", with text: " + update.getMessage().getText());
//
            botState.handleInput(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                } else {
                    break;
                }
            } while (!botState.getIsInputNeeded());
        }

        profile.setProfileBotState(botState);
        profileService.save(profile);
    }

//    private void processCallbackQuery(Update update) throws TelegramApiException {
//        final Long chatId = update.getCallbackQuery().getFrom().getId().longValue();
//        StudentBotContext botContext = null;
//        StudentBotState botState = null;
//
//        User user = userService.findByChatId(chatId);
//        Student student = user.getStudent();
//
//        if (student == null) {
//            student = studentService.createStudent(user);
//
//            botContext = StudentBotContext.of(this, student, update);
//            botState = student.getStudentBotState();
//
//            botState.enter(botContext);
//
//            while (!botState.getIsInputNeeded()) {
//                if (botState.nextState() != null) {
//                    botState = botState.nextState();
//                    botState.enter(botContext);
//                }
//            }
//        } else {
//            botContext = StudentBotContext.of(this, student, update);
//            botState = student.getStudentBotState();
//
//            logger.info("CallbackQuery received from user: " + chatId + ", in state: " + botState + ", with data: " + update.getCallbackQuery().getData());
//
//            if (botUtils.isEventVisitCallback(update.getCallbackQuery())) {
//                Event event = eventService.getById(botUtils.parseEventCallback(update.getCallbackQuery()));
//                Lead lead = leadService.getLeadByStudent(student);
//                EventLead eventLead = eventLeadService.getByEventAndLead(event, lead);
//
//                eventLeadService.updateEventLeadVisited(event, lead, true);
//
//                if (eventLead.getBotMessageId() != null && eventLead.getBotMessageDate() != null && !messageService.isMessageExpired(eventLead.getBotMessageDate())) {
//                    messageService.removeInlineKeyboard(user, eventLead.getBotMessageId(), eventLead.getBotMessageDate());
//                }
//                return;
//            } else if (botUtils.isTrialVisitCallback(update.getCallbackQuery())) {
//                Trial trial = trialService.getById(botUtils.parseTrialCallback(update.getCallbackQuery()));
//                Lead lead = leadService.getLeadByStudent(student);
//                TrialLead trialLead = trialLeadService.getByTrialAndLead(trial, lead);
//
//                trialLeadService.updateTrialLeadVisited(trial, lead, true);
//
//                if (trialLead.getBotMessageId() != null && trialLead.getBotMessageDate() != null && !messageService.isMessageExpired(trialLead.getBotMessageDate())) {
//                    DealLead dealLead = dealLeadService.getByLeadAndSubject(lead, trial.getSubject());
//
//                    if (dealLead.getPaid()) {
//                        messageService.removeInlineKeyboard(user, trialLead.getBotMessageId(), trialLead.getBotMessageDate());
//                    } else {
//                        messageService.editInlineKeyboard(user, inlineKeyboardSource.getPayInlineMarkup(), trialLead.getBotMessageId(), trialLead.getBotMessageDate());
//                    }
//                }
//                return;
//            } else if (botUtils.isPayCallback(update.getCallbackQuery())) {
//                subjectService.copyListSubject(student);
//                studentMessageService.sendSelectSubjectForPayment(student);
//                user.getStudent().setStudentBotState(StudentBotState.SelectionSubjectForPayment);
//                userService.save(user);
//                return;
//            } else if (update.getCallbackQuery().getData().equals("callback.write")) {
////                messageService.deleteBotLastMessage(user);
//                studentMessageService.sendSelectSubjectsToEventsMessage(student);
//                user.getStudent().setStudentBotState(StudentBotState.SelectEventSubjects);
//                userService.save(user);
//                return;
//            } else if (update.getCallbackQuery().getData().equals("signUpToTrial")) {
////                messageService.deleteBotLastMessage(user);
//                studentMessageService.sendEnterLeadLastname(student);
//                user.getStudent().setStudentBotState(StudentBotState.EnterLeadLastName);
//                userService.save(user);
//                return;
//            } else if (update.getCallbackQuery().getData().equals("callback.writeToTrial")) {
////                messageService.deleteBotLastMessage(user);
//                studentMessageService.sendSelectSubjectToTrialLesson(student);
//                user.getStudent().setStudentBotState(StudentBotState.SelectNewTrialLessonsSubjects);
//                userService.save(user);
//                return;
//            }
//
//            botState.handleCallbackQuery(botContext);
//
//            do {
//                if (botState.nextState() != null) {
//                    botState = botState.nextState();
//                    botState.enter(botContext);
//                } else {
//                    break;
//                }
//            } while (!botState.getIsInputNeeded());
//        }
//
//        if (user != null && user.getStudent() != null) {
//            user.getStudent().setStudentBotState(botState);
//            userService.save(user);
//        }
//    }
}