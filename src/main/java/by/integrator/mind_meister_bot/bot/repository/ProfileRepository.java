package by.integrator.mind_meister_bot.bot.repository;

import by.integrator.mind_meister_bot.bot.bean.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUserName(String userName);
    Profile findByChatId(Long chatId);

}
