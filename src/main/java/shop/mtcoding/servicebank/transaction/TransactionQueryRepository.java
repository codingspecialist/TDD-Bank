package shop.mtcoding.servicebank.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


// 네이티브 쿼리 예시
@RequiredArgsConstructor
@Repository
public class TransactionQueryRepository {
    private final EntityManager em;

    public void nativeQuery(){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, 1)
                .setParameter(2, 1)
                .setParameter(3, 2);

        // 쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDTO> subscribeDtos =  result.list(query, SubscribeDTO.class);
    }
}

class SubscribeDTO {

}