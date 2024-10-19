package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.service.CardNumberService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CardNumberServiceImpl implements CardNumberService {
    @Override
    public String getCardNumber() {
        String bin = "400000";
        int length = 16;

        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(bin);

//        while (cardNumber.length() < length - 1) {
//            int digit = random.nextInt(10);
//            cardNumber.append(digit);
//        }
//
//        int checkDigit = getLuhnCheckDigit(cardNumber.toString());
//        cardNumber.append(checkDigit);
        cardNumber.append("" + LocalDateTime.now().getDayOfMonth()
                + LocalDateTime.now().getMonth().getValue()
                + LocalDateTime.now().getYear()
                + LocalDateTime.now().getMinute());
        return cardNumber.toString();
    }

    private static int getLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }

}
