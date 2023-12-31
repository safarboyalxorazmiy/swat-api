package uz.jarvis.service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.jarvis.config.BotConfig;
import uz.jarvis.fridgePlan.FridgePlanEntity;
import uz.jarvis.fridgePlan.FridgePlanService;
import uz.jarvis.models.ModelEntity;
import uz.jarvis.models.ModelService;
import uz.jarvis.user.Role;
import uz.jarvis.user.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.jarvis.user.history.Label;
import uz.jarvis.user.history.UserHistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
  private final BotConfig config;
  private final UsersService usersService;
  private final UserHistoryService userHistoryService;
  private final ModelService modelService;
  private final FridgePlanService fridgePlanService;

  public TelegramBot(BotConfig config, UsersService usersService, UserHistoryService userHistoryService, ModelService modelService, FridgePlanService fridgePlanService) {
    this.config = config;
    this.usersService = usersService;
    this.userHistoryService = userHistoryService;
    this.modelService = modelService;
    this.fridgePlanService = fridgePlanService;

    List<BotCommand> listOfCommands = new ArrayList<>();
    listOfCommands.add(new BotCommand("/start", "Boshlash"));

    try {
      this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException e) {
      log.error("Error during setting bot's command list: {}", e.getMessage());
    }
  }

  @Override
  public String getBotUsername() {
    return config.getBotName();
  }

  @Override
  public String getBotToken() {
    return config.getToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      long chatId = update.getMessage().getChatId();
      if (update.getMessage().getChat().getType().equals("supergroup")) {
        // DO NOTHING CHANNEL CHAT ID IS -1001764816733
        return;
      } else {
        Role role = usersService.getRoleByChatId(chatId);

        if (update.hasMessage() && update.getMessage().hasText()) {
          String messageText = update.getMessage().getText();

          if (messageText.startsWith("/")) {
            if (messageText.startsWith("/login ")) {
              String password = messageText.substring(7);

              if (password.equals("Xp2s5v8y/B?E(H+KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r4u7x!A%D*G-Ka")) {
                usersService.changeRole(chatId, Role.ROLE_AGENT);
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());
                return;
              } else if (password.equals("674935b4fa5e4641853a42c43100de99")) {
                usersService.changeRole(chatId, Role.ROLE_OWNER);
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());
                return;
              }
              return;
            }

            switch (messageText) {
              case "/start" -> {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getLastName());
                return;
              }
              default -> {
                sendMessage(chatId, "Sorry, command was not recognized");
                return;
              }
            }
          }

          if (role.equals(Role.ROLE_AGENT)) {
            if (messageText.equals("Plan \uD83D\uDCED")) {
              List<String> models = new ArrayList<>();
              models.add("PRM-211");
              models.add("PRM-261");
              models.add("PRM-315");
              models.add("PRM-317");
              models.add("Bekor qilish \uD83D\uDD19");
              sendMessageWithKeyboardButtons(chatId, "Muzlatgich modelini tanlang..", models);

              userHistoryService.clearHistory(chatId);
              userHistoryService.create(Label.OFFER_STARTED, chatId, "NO_VALUE");
            }
            else if (messageText.equals("\uD83D\uDD19 Bosh menyuga qaytish")) {
              sendMessageWithKeyboardButtons(chatId, "Bosh menyu \uD83C\uDFD8", List.of("Plan \uD83D\uDCED", "Hisobot \uD83D\uDCDD"));
            }
            else if (messageText.equals("Bekor qilish \uD83D\uDD19")) {
              sendMessageWithKeyboardButtons(chatId, "Bosh menyu \uD83C\uDFD8", List.of("Plan \uD83D\uDCED", "Hisobot \uD83D\uDCDD"));
              userHistoryService.clearHistory(chatId);
            }
            else if (messageText.equals("Hisobot \uD83D\uDCDD")) {
              for (FridgePlanEntity fridgePlanEntity : fridgePlanService.findAll()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(
                  "<b>" + fridgePlanEntity.getModel().getName() + " " + fridgePlanEntity.getModel().getCode() + " " + fridgePlanEntity.getModel().getComment() + "</b> \n"
                    + "Plan: <b>" + fridgePlanEntity.getPlan().toString() + "</b>");

                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

                List<InlineKeyboardButton> row = new ArrayList<>();

                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(
                  "O'zgartirish ✍\uFE0F"
                );
                inlineKeyboardButton.setCallbackData("EditPlan: " + fridgePlanEntity.getModelId());

                row.add(inlineKeyboardButton);

                inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(
                  "O'chirish \uD83D\uDDD1"
                );
                inlineKeyboardButton.setCallbackData("DeletePlan: " + fridgePlanEntity.getModelId());

                row.add(inlineKeyboardButton);

                keyboard.add(row);

                markup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(markup);
                sendMessage.setParseMode("HTML");

                try {
                  execute(sendMessage);
                } catch (TelegramApiException e) {
                  throw new RuntimeException(e);
                }
              }
            }
            else {
              Label lastLabelByChatId = userHistoryService.getLastLabelByChatId(chatId);
              if (lastLabelByChatId != null) {
                if (lastLabelByChatId.equals(Label.OFFER_STARTED)) {
                  List<ModelEntity> models = modelService.findByName(messageText);

                  List<ModelEntity> locals = models.stream()
                    .filter(model -> model.getComment() != null && model.getComment().contains("LOC"))
                    .collect(Collectors.toList());

                  List<ModelEntity> exports = models.stream()
                    .filter(model -> model.getComment() != null && model.getComment().contains("EX"))
                    .collect(Collectors.toList());

                  models = new ArrayList<>();
                  models.addAll(locals);
                  models.addAll(exports);

                  SendMessage message = new SendMessage();
                  message.setChatId(chatId);
                  message.setText("⏳");

                  ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
                  replyKeyboardRemove.setRemoveKeyboard(true);
                  message.setReplyMarkup(replyKeyboardRemove);

                  try {
                    Message execute = execute(message);

                    deleteMessageById(chatId, execute.getMessageId());
                  } catch (TelegramApiException e) {
                    e.printStackTrace();
                  }

                  InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                  List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

                  for (ModelEntity model : models) {
                    List<InlineKeyboardButton> row = new ArrayList<>();

                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(
                      model.getName() + " - " + model.getCode() + " - " + model.getComment()
                    );
                    inlineKeyboardButton.setCallbackData("ModelId: " + model.getId());

                    row.add(inlineKeyboardButton);

                    keyboard.add(row);
                  }

                  message.setText("<b>" + messageText + "</b>ning modelini tanlang..");

                  markup.setKeyboard(keyboard);
                  message.setParseMode("HTML");
                  message.setReplyMarkup(markup);

                  try {
                    execute(message);
                  } catch (TelegramApiException e) {
                    e.printStackTrace();
                  }
                }
                else if (lastLabelByChatId.equals(Label.MODEL_ENTERED)) {
                  try {
                    Long modelId = Long.parseLong(userHistoryService.getLastValueByChatId(chatId, Label.MODEL_ENTERED));
                    Long plan = Long.parseLong(messageText);
                    fridgePlanService.create(modelId, plan);

                    sendMessageWithKeyboardButton(chatId, "Plan kiritildi. ✅", "\uD83D\uDD19 Bosh menyuga qaytish");
                  } catch (NumberFormatException e) {
                    sendMessage(chatId, "Iltimos aniq sonni kiriting misol uchun: 1234");
                  }
                } else if (lastLabelByChatId.equals(Label.EDITING_STARTED)) {
                  try {
                    Long modelId = Long.parseLong(userHistoryService.getLastValueByChatId(chatId, Label.EDITING_STARTED));
                    fridgePlanService.update(modelId, Long.valueOf(messageText));

                    sendMessageWithKeyboardButton(chatId, "Plan kiritildi. ✅", "\uD83D\uDD19 Bosh menyuga qaytish");
                  } catch (NumberFormatException e) {
                    sendMessage(chatId, "Iltimos aniq sonni kiriting misol uchun: 1234");
                  }
                }
              }
            }
          } else if (role.equals(Role.ROLE_USER)) {
          } else if (role.equals(Role.ROLE_OWNER)) {
          }
        }
        if (update.hasMessage() && update.getMessage().hasPhoto()) {

        }
      }

    } else if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      String data = callbackQuery.getData();
      Long chatId = callbackQuery.getMessage().getChatId();

      if (data.startsWith("ModelId: ")) {
        String modelId = data.substring("ModelId: ".length());
        userHistoryService.create(Label.MODEL_ENTERED, chatId, modelId);

        deleteMessageById(chatId, callbackQuery.getMessage().getMessageId());

        ModelEntity model = modelService.findById(Long.parseLong(modelId));
        sendMessage(chatId, "Model tanlandi!: <b>" + model.getName() + " " + model.getComment() + "</b> \uD83C\uDF89");
        sendMessageWithKeyboardButton(chatId, "Endi planni kiriting..", "Bekor qilish \uD83D\uDD19");
      } else if (data.startsWith("DeletePlan: ")) {
        String modelId = data.substring("DeletePlan: ".length());
        deleteMessageById(chatId, callbackQuery.getMessage().getMessageId());

        fridgePlanService.delete(Long.valueOf(modelId));
      } else if (data.startsWith("EditPlan: ")) {
        String modelId = data.substring("EditPlan: ".length());
        userHistoryService.create(Label.EDITING_STARTED, chatId, modelId);

        sendMessageWithKeyboardButton(chatId, "Planni kiriting..", "Bekor qilish \uD83D\uDD19");
      }
    }
  }

  private void startCommandReceived(long chatId, String firstName, String lastName) {
    Role role = usersService.createUser(chatId, firstName, lastName).getRole();

    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.enableHtml(true);

    if (role.equals(Role.ROLE_USER)) {
      message.setText("Welcome User, What's up?");
    } else if (role.equals(Role.ROLE_AGENT)) {
      message.setText("Hush kelibsiz, Agent!");

      ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
      List<KeyboardRow> rows = new ArrayList<>();
      KeyboardRow row = new KeyboardRow();
      row.add("Plan \uD83D\uDCED");
      row.add("Hisobot \uD83D\uDCDD");
      rows.add(row);
      replyKeyboardMarkup.setResizeKeyboard(true);
      replyKeyboardMarkup.setKeyboard(rows);

      message.setReplyMarkup(replyKeyboardMarkup);
    } else if (role.equals(Role.ROLE_OWNER)) {
      message.setText("Hush kelibsiz, Asoschi!");

      ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
      List<KeyboardRow> rows = new ArrayList<>();
      KeyboardRow row = new KeyboardRow();
      row.add("Mahsulot qo'shish ➕");
      rows.add(row);
      replyKeyboardMarkup.setResizeKeyboard(true);
      replyKeyboardMarkup.setKeyboard(rows);

      message.setReplyMarkup(replyKeyboardMarkup);
    }

    try {
      execute(message);
    } catch (TelegramApiException e) {
      log.error("Error in startCommandReceived()");
    }
  }

  private void sendMessage(long chatId, String textToSend) {
    SendMessage message = new SendMessage();

    message.setChatId(chatId);
    message.setText(textToSend);
    message.enableHtml(true);
    try {
      execute(message);
    } catch (TelegramApiException ignored) {
      log.error("Error in sendMessage()");
    }
  }

  private void sendMessageWithKeyboardButtons(long chatId, String textToSend, List<String> keyboardRowText) {
    SendMessage message = new SendMessage();

    message.setChatId(chatId);
    message.setText(textToSend);
    message.enableHtml(true);

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> rows = new ArrayList<>();
    if (keyboardRowText.size() < 3) {
      KeyboardRow row = new KeyboardRow();

      for (String s : keyboardRowText) {
        row.add(s);
      }

      rows.add(row);
    } else {
      for (String s : keyboardRowText) {
        KeyboardRow row = new KeyboardRow();
        row.add(s);
        rows.add(row);
      }
    }

    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setKeyboard(rows);

    message.setReplyMarkup(replyKeyboardMarkup);

    try {
      execute(message);
    } catch (TelegramApiException ignored) {
      log.error("Error in sendMessageWithKeyboardButton()");
    }
  }

  private void sendMessageWithKeyboardButton(long chatId, String textToSend, String keyboardRowText) {
    SendMessage message = new SendMessage();

    message.setChatId(chatId);
    message.setText(textToSend);
    message.enableHtml(true);

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> rows = new ArrayList<>();

    KeyboardRow row = new KeyboardRow();
    row.add(keyboardRowText);
    rows.add(row);


    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setKeyboard(rows);

    message.setReplyMarkup(replyKeyboardMarkup);

    try {
      execute(message);
    } catch (TelegramApiException ignored) {
      log.error("Error in sendMessageWithKeyboardButton()");
    }
  }

  public void deleteMessageById(Long chatId, Integer messageId) {
    try {
      DeleteMessage deleteMessage = new DeleteMessage();
      deleteMessage.setChatId(chatId);
      deleteMessage.setMessageId(messageId);

      execute(deleteMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}