package fileio;

public final class UserCredentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public UserCredentials() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCredentials{"
                + "name='"
                + name
                + '\''
                + ", password='"
                + password
                + '\''
                + ", accountType='"
                + accountType
                + '\''
                + ", country='"
                + country
                + '\''
                + ", balance="
                + balance
                + '}';
    }
}
