package shop.mtcoding.servicebank.transaction;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.servicebank._core.utils.DateUtils;
import shop.mtcoding.servicebank.account.Account;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionResponse {
    // 계좌 주인은 sender
    @Setter
    @Getter
    public static class WithdrawDTO {
        private Long id;
        private Integer number; // 계좌번호
        private Long balance; // 현재 잔액
        private String fullName;
        private List<TransactionDTO> transactions;

        public WithdrawDTO(Account account, List<Transaction> transactions) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.fullName = account.getUser().getFullName();
            this.transactions = transactions.stream().map((transaction -> new TransactionDTO(transaction))).collect(Collectors.toList());
        }

        @Setter
        @Getter
        public class TransactionDTO {
            private Long id;
            private String sender;
            private String receiver;
            private Long amount;
            private Long balance; // 이체시점 잔액
            private String createdAt;

            public TransactionDTO(Transaction transaction) {
                this.id = transaction.getId();
                this.sender = transaction.getWithdrawAccount().getNumber() + "";
                this.receiver = transaction.getDepositAccount().getNumber() + "";
                this.amount = transaction.getAmount();
                this.balance = transaction.getWithdrawAccountBalance();
                this.createdAt = DateUtils.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    // 계좌 주인은 receiver
    @Setter
    @Getter
    public static class DepositDTO {
        private Long id;
        private Integer number; // 계좌번호
        private Long balance; // 잔액
        private String fullName;
        private List<TransactionDTO> transactions;

        public DepositDTO(Account account, List<Transaction> transactions) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.fullName = account.getUser().getFullName();
            this.transactions = transactions.stream().map((transaction -> new TransactionDTO(transaction))).collect(Collectors.toList());
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
                this.sender = transaction.getWithdrawAccount().getNumber() + "";
                this.receiver = transaction.getDepositAccount().getNumber() + "";
                this.amount = transaction.getAmount();
                this.balance = transaction.getDepositAccountBalance();
                this.createdAt = DateUtils.toStringFormat(transaction.getCreatedAt());
            }
        }
    }

    // 계좌주인은 상황에 따라 다름 (출금 or 입금)
    @Setter
    @Getter
    public static class DepositAndWithDrawDTO {
        private Long id;
        private Integer number; // 계좌번호
        private Long balance; // 잔액
        private String fullName;
        private List<TransactionDTO> transactions;

        public DepositAndWithDrawDTO(Account account, List<Transaction> transactions) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.fullName = account.getUser().getFullName();
            this.transactions = transactions.stream().map((transaction -> new TransactionDTO(transaction, account))).collect(Collectors.toList());
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

            public TransactionDTO(Transaction transaction, Account account) {
                this.id = transaction.getId();
                this.sender = transaction.getWithdrawAccount().getNumber() + "";
                this.receiver = transaction.getDepositAccount().getNumber() + "";
                this.amount = transaction.getAmount();
                if (account.getNumber() == transaction.getWithdrawAccount().getNumber()) {
                    this.balance = transaction.getWithdrawAccountBalance();
                } else {
                    this.balance = transaction.getDepositAccountBalance();
                }
                this.createdAt = DateUtils.toStringFormat(transaction.getCreatedAt());
            }
        }
    }
}
