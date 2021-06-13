package se.kronansapotek.personal_id;

import org.springframework.stereotype.Component;

@Component
public class PersonalIdService {

    public boolean isValid(String id) {
        if (!hasValidFormat(id)) {
            return false;
        }
        String formattedId = formatId(id);
        return hasCorrectChecksum(formattedId);
    }

    private boolean hasValidFormat(String id) {
        return id.matches("(\\d{6}|\\d{8})(\\+|-?)(\\d{4})");
    }

    private String formatId(String id) {
        String formattedId = id.replaceAll("[+-]", "");
        return formattedId.length() > 10 ? formattedId.substring(2) : formattedId;
    }

    private boolean hasCorrectChecksum(String id) {
        int checksum = 0;
        for (int i = 0; i < id.length() - 1; i++) {
            int digit = Character.digit(id.charAt(i), 10);
            int product = i % 2 == 0 ? digit * 2 : digit;
            checksum += (product / 10) + (product % 10);
        }
        checksum = checksum % 10 == 0 ? 0 : 10 - (checksum % 10);

        return checksum == Character.digit(id.charAt(id.length() - 1), 10);
    }
}
