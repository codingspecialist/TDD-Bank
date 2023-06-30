package shop.mtcoding.servicebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.servicebank._core.erros.exception.Exception401;
import shop.mtcoding.servicebank._core.erros.exception.Exception403;
import shop.mtcoding.servicebank._core.security.MyUserDetails;
import shop.mtcoding.servicebank._core.utils.ApiUtils;
import shop.mtcoding.servicebank.dto.account.AccountRequest;
import shop.mtcoding.servicebank.dto.account.AccountResponse;
import shop.mtcoding.servicebank.service.AccountService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    private final HttpSession session;

    @PostMapping("/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountRequest.SaveDTO saveDTO, Errors errors, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        AccountResponse.SaveDTO responseBody = accountService.계좌등록(saveDTO, myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/account/{number}")
    public ResponseEntity<?> findAccountDetail(@PathVariable Integer number, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        AccountResponse.DetailDTO responseBody = accountService.계좌상세보기(number, myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/account")
    public ResponseEntity<?> findUserAccountList(Long userId, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(userId.longValue() != myUserDetails.getUser().getId()){
            throw new Exception403("해당 계좌목록을 볼 수 있는 권한이 없습니다");
        }

        AccountResponse.ListDTO responseBody = accountService.유저계좌목록보기(userId);
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @PostMapping("/account/transfer")
    public ResponseEntity<?> transferAccount(@RequestBody @Valid AccountRequest.TransferDTO transferDTO,
                                             Errors errors, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        AccountResponse.TransferDTO responseBody = accountService.계좌이체(transferDTO, myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }
}
