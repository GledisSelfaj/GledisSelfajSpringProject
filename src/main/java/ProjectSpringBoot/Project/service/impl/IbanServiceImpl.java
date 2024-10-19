package ProjectSpringBoot.Project.service.impl;

import ProjectSpringBoot.Project.service.IbanService;
import org.springframework.stereotype.Service;

@Service
public class IbanServiceImpl implements IbanService {
    @Override
    public String generateIban(String countryCode, String Bban) {
        if (countryCode == null || Bban == null) {
            throw new IllegalArgumentException("Country code and BBAN cannot be null");
        }
        if (countryCode.length() != 2) {
            throw new IllegalArgumentException("Country code must be 2 letters long");
        }

        String countryCodeNumeric = convertLettersToDigits(countryCode);

        String ibanWithoutCheckDigits = Bban + countryCodeNumeric + "00";

        String checkDigits = calculateCheckDigits(ibanWithoutCheckDigits);

        return countryCode + checkDigits + Bban;
    }

    private String convertLettersToDigits(String letters) {
        StringBuilder numeric = new StringBuilder();
        for (char ch : letters.toCharArray()) {
            if (Character.isLetter(ch)) {
                numeric.append(Character.toUpperCase(ch) - 'A' + 10);
            } else {
                numeric.append(ch);
            }
        }
        return numeric.toString();
    }

    private String calculateCheckDigits(String ibanWithoutCheckDigits) {

        String ibanNumeric = convertLettersToDigits(ibanWithoutCheckDigits);


        int remainder = mod97(ibanNumeric);
        int checkDigits = 98 - remainder;
        return String.format("%02d", checkDigits);
    }

    private int mod97(String number) {
        int remainder = 0;
        for (char digit : number.toCharArray()) {
            remainder = (remainder * 10 + Character.getNumericValue(digit)) % 97;
        }
        return remainder;
    }
}

