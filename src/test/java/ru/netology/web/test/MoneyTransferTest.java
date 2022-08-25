package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
          void startUp() {
    open("http://localhost:9999");
  }
  @ParameterizedTest
  @CsvSource(value = {
          "0",
          "1",
          "9999",
  })
    void shouldTransferMoneyFromFirstCardToSecond(int amount) {
      var loginPage = new LoginPageV1();
      var authInfo = DataHelper.getAuthInfo();
      var cardInfo = DataHelper.getCardInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      var dashboardPage = verificationPage.validVerify(verificationCode);
      var firstCardInfo = cardInfo.getFirstCard();
      var secondCardInfo = cardInfo.getSecondCard();
      var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo) - amount;
      var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo) + amount;
      var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
      dashboardPage = transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
      var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
      var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
      assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
      assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

  @ParameterizedTest
  @CsvSource(value = {
          "0",
          "1",
          "9999",
  })
  void shouldTransferMoneyFromSecondCardToFirst(int amount) {
    var loginPage = new LoginPageV1();
    var authInfo = DataHelper.getAuthInfo();
    var cardInfo = DataHelper.getCardInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    var firstCardInfo = cardInfo.getFirstCard();
    var secondCardInfo = cardInfo.getSecondCard();
    var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo) + amount;
    var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo) - amount;
    var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
    dashboardPage = transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
    var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }
}

