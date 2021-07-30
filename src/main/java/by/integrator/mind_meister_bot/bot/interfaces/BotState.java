package by.integrator.mind_meister_bot.bot.interfaces;

public interface BotState<E extends Enum<E>, T> {
    public void handleInput(T t);
    public void handleCallbackQuery(T t);

    public abstract void enter(T t);
    public abstract E nextState();
}
