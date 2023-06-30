package shop.mtcoding.servicebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.servicebank._core.erros.exception.Exception400;
import shop.mtcoding.servicebank._core.erros.exception.Exception401;
import shop.mtcoding.servicebank._core.security.MyUserDetails;
import shop.mtcoding.servicebank._core.utils.ApiUtils;

import shop.mtcoding.servicebank.dto.transaction.TransactionResponse;
import shop.mtcoding.servicebank.service.TransactionService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final HttpSession session;

    @GetMapping("/account/{number}/transaction")
    public ResponseEntity<?> findTransaction(@PathVariable Integer number,
                                             @RequestParam(defaultValue = "all") String gubun,
                                             @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(gubun.equals("all")){
            TransactionResponse.DepositAndWithDrawDTO responseBody = transactionService.입출금내역보기(number, myUserDetails.getUser().getId());
            return ResponseEntity.ok().body(ApiUtils.success(responseBody));
        }else if(gubun.equals("withdraw")){
            TransactionResponse.WithdrawDTO responseBody = transactionService.출금내역보기(number, myUserDetails.getUser().getId());
            return ResponseEntity.ok(ApiUtils.success(responseBody));
        }else if(gubun.equals("deposit")){
            TransactionResponse.DepositDTO responseBody = transactionService.입금내역보기(number, myUserDetails.getUser().getId());
            return ResponseEntity.ok(ApiUtils.success(responseBody));
        }else{
            throw new Exception400("gubun", "잘못된 요청을 하였습니다 : "+gubun);
        }
    }
}
