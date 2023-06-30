package shop.mtcoding.servicebank.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.servicebank.model.user.User;

// 응답 DTO는 서비스 배우고 나서 하기 (할 수 있으면 해보기)
public class UserResponse {
    @Setter @Getter
    public static class JoinDTO {
        private Long id;
        private String username;
        private String fullName;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullName = user.getFullName();
        }
    }
}
