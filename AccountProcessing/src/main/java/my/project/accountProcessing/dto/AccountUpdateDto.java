package my.project.accountProcessing.dto;

import lombok.Builder;
import lombok.Getter;
import my.lib.core.StatusEnum;

@Builder
@Getter
public class AccountUpdateDto {

        private Long accountId;
        private Boolean cardExist;
        private StatusEnum status;

}
