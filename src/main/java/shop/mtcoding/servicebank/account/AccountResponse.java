package shop.mtcoding.servicebank.account;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.servicebank._core.utils.DateUtils;
import shop.mtcoding.servicebank.transaction.Transaction;
import shop.mtcoding.servicebank.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class AccountResponse {

    @Setter @Getter
    public static class SaveDTO {
        private Long id;
        private Integer number;
        private Long balance;

        public SaveDTO(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }

    @Setter @Getter
    public static class DetailDTO {
        private Long id;
        private String fullName;
        private Integer number;
        private Long balance;
        private String createdAt;

        public DetailDTO(Account account) {
            this.id = account.getId();
            this.fullName = account.getUser().getFullName();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.createdAt = DateUtils.toStringFormat(account.getCreatedAt());
        }
    }

    @Setter @Getter
    public static class ListDTO {
        private String fullName;
        private List<AccountDTO> accounts;

        public ListDTO(User user, List<Account> accounts) {
            this.fullName = user.getFullName();
            this.accounts = accounts.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        }

        @Setter @Getter
        public class AccountDTO {
            private Long id;
            private Integer number;
            private Long balance;

            public AccountDTO(Account account) {
                this.id = account.getId();
                this.number = account.getNumber();
                this.balance = account.getBalance();
            }
        }
    }

    @Setter
    @Getter
    public static class TransferDTO {
        private Long id;
        private Integer number;
        private Long balance;
        private TransactionDTO transaction;

        public TransferDTO(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDTO(transaction);
        }

        @Setter
        @Getter
        public class TransactionDTO {
            private Long id;
            private String sender;
            private String receiver;
            private Long amount;
            private Long balance;
            private String createdAt;

            public TransactionDTO(Transaction transaction) {
                this.id = transaction.getId();
                this.sender = transaction.getWithdrawAccount().getNumber()+"";
                this.receiver = transaction.getDepositAccount().getNumber()+"";
                this.amount = transaction.getAmount();
                this.balance = transaction.getWithdrawAccountBalance();
                this.createdAt = DateUtils.toStringFormat(transaction.getCreatedAt());
            }
        }
    }
}
