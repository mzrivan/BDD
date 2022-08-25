package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private String balanceStart = "баланс: ";
  private String balanceFinish = " р.";
  private ElementsCollection cards = $$(".list__item");

  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public int getCardBalance(String cardInfo) {
    var text = cards.findBy(text(cardInfo.substring(12, 16))).getText();
    var start = text.indexOf(balanceStart);
    var finish = text.indexOf(balanceFinish);
    var value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
  public TransferPage selectCardToTransfer(String cardInfo) {
    cards.findBy(text(cardInfo.substring(12, 16))).$("button").click();
    return new TransferPage();
  }
}
