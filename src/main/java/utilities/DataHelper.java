package utilities;


import net.datafaker.Faker;
import net.datafaker.providers.base.Finance;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class DataHelper {

    private static DataHelper dataHelper;
    private final Faker faker = new Faker(Locale.US);

    private DataHelper() {
    }

    public static synchronized DataHelper getDataHelper() {
        return dataHelper == null ? new DataHelper() : dataHelper;
    }

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getUserEmail() {
        return generateUniqueUUID() + faker.internet().emailAddress();
    }

    public String getPassword() {
        return faker.internet().password();
    }

    public Date getBirthday() {
        return faker.date().birthday();
    }

    public String getCompanyName() {
        return faker.company().name();
    }

    public String getHumanGender() {
        return faker.demographic().sex();
    }

    public String getCountry() {
        return faker.country().name();
    }

    public String getProvince() {
        return faker.address().state();
    }

    public String getProductName() {
        return faker.commerce().productName();
    }

    public String getCity() {
        return faker.address().city();
    }

    public String getAddress() {
        return faker.address().streetAddress();
    }

    public String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public String getPostalCode() {
        return faker.address().zipCode();
    }

    public String getFaxNumber() {
        return "0" + faker.phoneNumber().phoneNumber().substring(1);
    }

    private String generateUniqueUUID() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array()).substring(0, 10);
    }

    public String getVisaCarNumber() {
        return faker.finance().creditCard(Finance.CreditCardType.VISA);
    }

    public String getCardCodeNumber() {
        return faker.number().digits(3);
    }

}